"""配置文件系统，支持加载、验证和环境变量覆盖"""

import copy
import logging
import os
from dataclasses import dataclass, field
from pathlib import Path
from typing import Any, Optional

import yaml

logger = logging.getLogger(__name__)

PROJECT_ROOT = Path(__file__).resolve().parent.parent
CONFIG_DIR = PROJECT_ROOT / "config"
DEFAULT_PLATFORMS_PATH = CONFIG_DIR / "platforms.yaml"
DEFAULT_FILTERS_PATH = CONFIG_DIR / "filters.yaml"


class ConfigError(Exception):
    pass


class ConfigValidationError(ConfigError):
    pass


@dataclass
class ValidationResult:
    valid: bool = True
    errors: list[str] = field(default_factory=list)
    warnings: list[str] = field(default_factory=list)

    def add_error(self, message: str) -> None:
        self.errors.append(message)
        self.valid = False

    def add_warning(self, message: str) -> None:
        self.warnings.append(message)


def load_yaml_file(path: Path | str) -> dict[str, Any]:
    path = Path(path)
    if not path.exists():
        raise ConfigError(f"配置文件不存在: {path}")
    if not path.is_file():
        raise ConfigError(f"路径不是文件: {path}")

    try:
        with open(path, "r", encoding="utf-8") as f:
            data = yaml.safe_load(f)
        return data if isinstance(data, dict) else {}
    except yaml.YAMLError as e:
        raise ConfigError(f"YAML 解析错误 ({path}): {e}")


def save_yaml_file(data: dict[str, Any], path: Path | str) -> None:
    path = Path(path)
    path.parent.mkdir(parents=True, exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        yaml.dump(data, f, allow_unicode=True, default_flow_style=False, sort_keys=False)


def apply_env_overrides(config: dict[str, Any], prefix: str = "BOUNTY_HUNTER_") -> dict[str, Any]:
    result = copy.deepcopy(config)

    for key, value in os.environ.items():
        if not key.startswith(prefix):
            continue

        config_path = key[len(prefix):].lower().split("__")
        _set_nested_value(result, config_path, value)

    return result


def _set_nested_value(data: dict[str, Any], keys: list[str], value: str) -> None:
    current = data
    for i, k in enumerate(keys[:-1]):
        if k not in current:
            current[k] = {}
        elif not isinstance(current[k], dict):
            current[k] = {}
        current = current[k]

    final_key = keys[-1]
    current[final_key] = _coerce_value(value)


def _coerce_value(value: str) -> Any:
    if value.lower() in ("true", "yes", "1"):
        return True
    if value.lower() in ("false", "no", "0"):
        return False
    try:
        return int(value)
    except ValueError:
        pass
    try:
        return float(value)
    except ValueError:
        pass
    return value


def validate_platforms_config(config: dict[str, Any]) -> ValidationResult:
    result = ValidationResult(valid=True)

    if not isinstance(config, dict):
        result.add_error("平台配置必须是字典类型")
        return result

    required_fields = ["name", "url"]
    optional_fields = ["api_base", "payment_method", "typical_bounty_range", "features", "rate_limit"]

    for platform_id, platform_cfg in config.items():
        if not isinstance(platform_cfg, dict):
            result.add_warning(f"平台 '{platform_id}' 配置不是字典类型，跳过验证")
            continue

        for req_field in required_fields:
            if req_field not in platform_cfg:
                result.add_error(f"平台 '{platform_id}' 缺少必要字段: {req_field}")

        bounty_range = platform_cfg.get("typical_bounty_range")
        if isinstance(bounty_range, dict):
            min_val = bounty_range.get("min")
            max_val = bounty_range.get("max")
            if (
                isinstance(min_val, (int, float))
                and isinstance(max_val, (int, float))
                and min_val > max_val
            ):
                result.add_error(
                    f"平台 '{platform_id}' 的 typical_bounty_range.min ({min_val}) "
                    f"> max ({max_val})"
                )

        rate_limit = platform_cfg.get("rate_limit")
        if isinstance(rate_limit, dict):
            rpm = rate_limit.get("requests_per_minute")
            if isinstance(rpm, (int, float)) and rpm <= 0:
                result.add_error(
                    f"平台 '{platform_id}' 的 rate_limit.requests_per_minute 必须大于 0"
                )

        url = platform_cfg.get("url", "")
        if url and not url.startswith(("http://", "https://")):
            result.add_warning(f"平台 '{platform_id}' 的 URL 格式可能不正确: {url}")

    return result


def validate_filters_config(config: dict[str, Any]) -> ValidationResult:
    result = ValidationResult(valid=True)

    if not isinstance(config, dict):
        result.add_error("筛选配置必须是字典类型")
        return result

    bounty = config.get("bounty")
    if isinstance(bounty, dict):
        min_amt = bounty.get("min_amount")
        if isinstance(min_amt, (int, float)) and min_amt < 0:
            result.add_error("bounty.min_amount 不能为负数")

    weights = config.get("evaluation", {}).get("weights")
    if isinstance(weights, dict):
        total_weight = sum(v for v in weights.values() if isinstance(v, (int, float)))
        if abs(total_weight - 1.0) > 0.001:
            result.add_warning(f"评估权重总和为 {total_weight:.3f}，建议为 1.0")

        for dim_name, weight in weights.items():
            if isinstance(weight, (int, float)) and (weight < 0 or weight > 1):
                result.add_error(f"评估权重 '{dim_name}' 值 {weight} 不在 [0, 1] 范围内")

    thresholds = config.get("evaluation", {}).get("thresholds")
    if isinstance(thresholds, dict):
        sorted_thresholds = sorted(
            ((k, v) for k, v in thresholds.items() if isinstance(v, (int, float))),
            key=lambda x: x[1],
            reverse=True,
        )
        for i in range(len(sorted_thresholds) - 1):
            if sorted_thresholds[i][1] < sorted_thresholds[i + 1][1]:
                result.add_warning(
                    f"阈值顺序可能异常: {sorted_thresholds[i][0]}={sorted_thresholds[i][1]} "
                    f"< {sorted_thresholds[i + 1][0]}={sorted_thresholds[i + 1][1]}"
                )

    languages = config.get("languages")
    if isinstance(languages, list):
        seen = set()
        for lang in languages:
            if lang in seen:
                result.add_warning(f"语言列表中存在重复项: {lang}")
            seen.add(lang)

    difficulty = config.get("difficulty")
    if isinstance(difficulty, dict):
        pref_range = difficulty.get("preferred_range", {})
        if isinstance(pref_range, dict):
            pmin = pref_range.get("min")
            pmax = pref_range.get("max")
            if (
                isinstance(pmin, (int, float))
                and isinstance(pmax, (int, float))
                and pmin >= pmax
            ):
                result.add_error(
                    f"difficulty.preferred_range.min ({pmin}) 必须 < max ({pmax})"
                )

    timeliness = config.get("timeliness")
    if isinstance(timeliness, dict):
        score_tiers = timeliness.get("score_tiers", {})
        if isinstance(score_tiers, dict):
            for tier_name, score in score_tiers.items():
                if isinstance(score, (int, float)) and (score < 0 or score > 10):
                    result.add_error(
                        f"timeliness.score_tiers.{tier_name} 值 {score} 不在 [0, 10] 范围内"
                    )

    search = config.get("search")
    if isinstance(search, dict):
        per_page = search.get("per_page")
        if isinstance(per_page, int) and (per_page < 1 or per_page > 100):
            result.add_warning(f"search.per_page={per_page} 可能超出合理范围 (1-100)")

    return result


def get_platform_config(platform_id: Optional[str] = None) -> dict[str, Any]:
    config = load_yaml_file(DEFAULT_PLATFORMS_PATH)
    config = apply_env_overrides(config)

    if platform_id:
        if platform_id not in config:
            raise ConfigError(f"未找到平台配置: {platform_id}")
        return {platform_id: config[platform_id]}

    return config


def get_filters_config() -> dict[str, Any]:
    config = load_yaml_file(DEFAULT_FILTERS_PATH)
    config = apply_env_overrides(config)
    return config


def get_full_config() -> dict[str, Any]:
    platforms = get_platform_config()
    filters = get_filters_config()
    return {
        "platforms": platforms,
        "filters": filters,
    }


def validate_all_configs() -> tuple[ValidationResult, ValidationResult]:
    platforms_result = validate_platforms_config(get_platform_config())
    filters_result = validate_filters_config(get_filters_config())

    for w in filters_result.warnings:
        logger.warning(f"筛选配置警告: {w}")
    for e in filters_result.errors:
        logger.error(f"筛选配置错误: {e}")
    for w in platforms_result.warnings:
        logger.warning(f"平台配置警告: {w}")
    for e in platforms_result.errors:
        logger.error(f"平台配置错误: {e}")

    return platforms_result, filters_result


def get_search_labels(platform_id: str = "github") -> list[str]:
    config = get_platform_config(platform_id)
    platform_cfg = config.get(platform_id, {})
    return platform_cfg.get("search_labels", [])


def get_supported_languages() -> list[str]:
    config = get_filters_config()
    return config.get("languages", [])


def get_evaluation_weights() -> dict[str, float]:
    config = get_filters_config()
    return config.get("evaluation", {}).get("weights", {})


def get_evaluation_thresholds() -> dict[str, float]:
    config = get_filters_config()
    return config.get("evaluation", {}).get("thresholds", {})
