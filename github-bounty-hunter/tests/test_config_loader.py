"""配置文件系统单元测试"""

import os
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

sys.path.insert(0, os.path.join(os.path.dirname(__file__), ".."))

from scripts.config_loader import (
    ConfigError,
    ConfigValidationError,
    ValidationResult,
    apply_env_overrides,
    get_evaluation_thresholds,
    get_evaluation_weights,
    get_filters_config,
    get_full_config,
    get_platform_config,
    get_search_labels,
    get_supported_languages,
    load_yaml_file,
    save_yaml_file,
    validate_all_configs,
    validate_filters_config,
    validate_platforms_config,
)


class TestLoadYamlFile(unittest.TestCase):
    def test_load_valid_yaml(self):
        with tempfile.NamedTemporaryFile(mode="w", suffix=".yaml", delete=False) as f:
            f.write("key: value\nnested:\n  a: 1\n  b: 2\n")
            f.flush()
            result = load_yaml_file(f.name)
        os.unlink(f.name)
        self.assertEqual(result["key"], "value")
        self.assertEqual(result["nested"]["a"], 1)

    def test_load_nonexistent_file(self):
        with self.assertRaises(ConfigError):
            load_yaml_file("/nonexistent/path/config.yaml")

    def test_load_directory_raises(self):
        with tempfile.TemporaryDirectory() as tmpdir:
            with self.assertRaises(ConfigError):
                load_yaml_file(tmpdir)

    def test_load_invalid_yaml(self):
        with tempfile.NamedTemporaryFile(mode="w", suffix=".yaml", delete=False) as f:
            f.write("key: [invalid\n  unclosed")
            f.flush()
            with self.assertRaises(ConfigError):
                load_yaml_file(f.name)
        os.unlink(f.name)

    def test_load_empty_file_returns_dict(self):
        with tempfile.NamedTemporaryFile(mode="w", suffix=".yaml", delete=False) as f:
            f.write("")
            f.flush()
            result = load_yaml_file(f.name)
        os.unlink(f.name)
        self.assertEqual(result, {})


class TestSaveYamlFile(unittest.TestCase):
    def test_save_and_reload(self):
        with tempfile.NamedTemporaryFile(suffix=".yaml", delete=False) as f:
            path = f.name

        data = {"test": {"nested": "value", "list": [1, 2, 3]}}
        save_yaml_file(data, path)
        reloaded = load_yaml_file(path)
        self.assertEqual(reloaded["test"]["nested"], "value")
        self.assertEqual(reloaded["test"]["list"], [1, 2, 3])
        os.unlink(path)

    def test_save_creates_parent_dirs(self):
        tmpdir = tempfile.mkdtemp()
        path = os.path.join(tmpdir, "sub", "dir", "config.yaml")
        save_yaml_file({"key": "value"}, path)
        self.assertTrue(os.path.exists(path))
        import shutil
        shutil.rmtree(tmpdir)


class TestApplyEnvOverrides(unittest.TestCase):
    def test_simple_override(self):
        config = {"bounty": {"min_amount": 20}}
        env = {"BOUNTY_HUNTER_BOUNTY__MIN_AMOUNT": "100"}
        with patch.dict(os.environ, env, clear=True):
            result = apply_env_overrides(config)
        self.assertEqual(result["bounty"]["min_amount"], 100)

    def test_no_override_when_prefix_mismatch(self):
        config = {"bounty": {"min_amount": 20}}
        env = {"OTHER_VAR__VALUE": "50"}
        with patch.dict(os.environ, env, clear=True):
            result = apply_env_overrides(config)
        self.assertEqual(result["bounty"]["min_amount"], 20)

    def test_boolean_coercion(self):
        config = {"notification": {"enabled": False}}
        env = {"BOUNTY_HUNTER_NOTIFICATION__ENABLED": "true"}
        with patch.dict(os.environ, env, clear=True):
            result = apply_env_overrides(config)
        self.assertTrue(result["notification"]["enabled"])

    def test_integer_coercion(self):
        config = {"search": {"per_page": 20}}
        env = {"BOUNTY_HUNTER_SEARCH__PER_PAGE": "50"}
        with patch.dict(os.environ, env, clear=True):
            result = apply_env_overrides(config)
        self.assertEqual(result["search"]["per_page"], 50)

    def test_float_coercion(self):
        config = {"evaluation": {"weights": {"difficulty": 0.25}}}
        env = {"BOUNTY_HUNTER_EVALUATION__WEIGHTS__DIFFICULTY": "0.30"}
        with patch.dict(os.environ, env, clear=True):
            result = apply_env_overrides(config)
        self.assertAlmostEqual(result["evaluation"]["weights"]["difficulty"], 0.30)

    def test_deep_nesting(self):
        config = {"a": {"b": {"c": {"d": "original"}}}}
        env = {"BOUNTY_HUNTER_A__B__C__D": "overridden"}
        with patch.dict(os.environ, env, clear=True):
            result = apply_env_overrides(config)
        self.assertEqual(result["a"]["b"]["c"]["d"], "overridden")

    def test_does_not_mutate_original(self):
        config = {"key": "original"}
        env = {"BOUNTY_HUNTER_KEY": "changed"}
        with patch.dict(os.environ, env, clear=True):
            result = apply_env_overrides(config)
        self.assertEqual(config["key"], "original")
        self.assertEqual(result["key"], "changed")


class TestValidatePlatformsConfig(unittest.TestCase):
    def test_valid_platforms_config(self):
        config = {
            "issuehunt": {
                "name": "IssueHunt",
                "url": "https://issuehunt.io",
                "typical_bounty_range": {"min": 20, "max": 200},
            }
        }
        result = validate_platforms_config(config)
        self.assertTrue(result.valid)
        self.assertEqual(len(result.errors), 0)

    def test_missing_required_field(self):
        config = {
            "test_platform": {
                "name": "Test",
            }
        }
        result = validate_platforms_config(config)
        self.assertFalse(result.valid)
        self.assertTrue(any("url" in e for e in result.errors))

    def test_bounty_range_inverted(self):
        config = {
            "bad_platform": {
                "name": "Bad",
                "url": "https://example.com",
                "typical_bounty_range": {"min": 500, "max": 50},
            }
        }
        result = validate_platforms_config(config)
        self.assertFalse(result.valid)
        self.assertTrue(any("min" in e and "max" in e for e in result.errors))

    def test_non_dict_platform_entry(self):
        config = {"weird": "not_a_dict"}
        result = validate_platforms_config(config)
        self.assertTrue(result.valid)
        self.assertTrue(any("不是字典" in w for w in result.warnings))

    def test_url_without_protocol(self):
        config = {
            "platform": {
                "name": "P",
                "url": "example.com",
            }
        }
        result = validate_platforms_config(config)
        self.assertTrue(any("URL 格式" in w for w in result.warnings))

    def test_rate_limit_zero(self):
        config = {
            "platform": {
                "name": "P",
                "url": "https://example.com",
                "rate_limit": {"requests_per_minute": 0},
            }
        }
        result = validate_platforms_config(config)
        self.assertFalse(result.valid)

    def test_not_a_dict_type(self):
        result = validate_platforms_config("not a dict")
        self.assertFalse(result.valid)
        self.assertTrue(any("字典类型" in e for e in result.errors))


class TestValidateFiltersConfig(unittest.TestCase):
    def test_valid_filters_config(self):
        config = {
            "bounty": {"min_amount": 20},
            "evaluation": {
                "weights": {"difficulty": 0.25, "amount": 0.25, "competition": 0.25, "timeliness": 0.15, "project_quality": 0.10},
                "thresholds": {"highly_recommended": 7.5, "recommended": 6.0, "maybe": 4.5},
            },
            "languages": ["python", "typescript"],
        }
        result = validate_filters_config(config)
        self.assertTrue(result.valid)

    def test_negative_min_amount(self):
        config = {"bounty": {"min_amount": -10}}
        result = validate_filters_config(config)
        self.assertFalse(result.valid)
        self.assertTrue(any("负数" in e for e in result.errors))

    def test_weights_dont_sum_to_one_warning(self):
        config = {
            "evaluation": {
                "weights": {"difficulty": 0.7, "amount": 0.3, "extra": 0.2},
            }
        }
        result = validate_filters_config(config)
        self.assertTrue(result.valid)
        self.assertTrue(len(result.warnings) > 0)

    def test_weight_out_of_range(self):
        config = {
            "evaluation": {
                "weights": {"difficulty": 1.5},
            }
        }
        result = validate_filters_config(config)
        self.assertFalse(result.valid)

    def test_duplicate_languages(self):
        config = {"languages": ["python", "python", "rust"]}
        result = validate_filters_config(config)
        self.assertTrue(any("重复项" in w for w in result.warnings))

    def test_preferred_range_inverted(self):
        config = {
            "difficulty": {
                "preferred_range": {"min": 8, "max": 3},
            }
        }
        result = validate_filters_config(config)
        self.assertFalse(result.valid)

    def test_score_tier_out_of_range(self):
        config = {
            "timeliness": {
                "score_tiers": {"fresh_3d": -1},
            }
        }
        result = validate_filters_config(config)
        self.assertFalse(result.valid)

    def test_per_page_extreme_value(self):
        config = {"search": {"per_page": 999}}
        result = validate_filters_config(config)
        self.assertTrue(any("per_page" in w for w in result.warnings))


class TestGetPlatformConfig(unittest.TestCase):
    def test_get_specific_platform(self):
        config = get_platform_config("issuehunt")
        self.assertIn("issuehunt", config)
        self.assertEqual(config["issuehunt"]["name"], "IssueHunt")

    def test_get_all_platforms(self):
        config = get_platform_config()
        self.assertIn("issuehunt", config)
        self.assertIn("github", config)

    def test_get_nonexistent_platform_raises(self):
        with self.assertRaises(ConfigError):
            get_platform_config("nonexistent_platform_xyz")


class TestGetFiltersConfig(unittest.TestCase):
    def test_returns_dict(self):
        config = get_filters_config()
        self.assertIsInstance(config, dict)
        self.assertIn("bounty", config)
        self.assertIn("languages", config)

    def test_has_languages(self):
        config = get_filters_config()
        languages = config.get("languages", [])
        self.assertIn("python", languages)
        self.assertIn("typescript", languages)


class TestGetFullConfig(unittest.TestCase):
    def test_contains_both(self):
        full = get_full_config()
        self.assertIn("platforms", full)
        self.assertIn("filters", full)
        self.assertIsInstance(full["platforms"], dict)
        self.assertIsInstance(full["filters"], dict)


class TestValidateAllConfigs(unittest.TestCase):
    def test_runs_without_error(self):
        p_result, f_result = validate_all_configs()
        self.assertIsInstance(p_result, ValidationResult)
        self.assertIsInstance(f_result, ValidationResult)


class TestHelperFunctions(unittest.TestCase):
    def test_get_search_labels_default(self):
        labels = get_search_labels()
        self.assertIsInstance(labels, list)
        self.assertIn("bounty", labels)

    def test_get_supported_languages(self):
        langs = get_supported_languages()
        self.assertIsInstance(langs, list)
        self.assertIn("python", langs)
        self.assertIn("go", langs)

    def test_get_evaluation_weights(self):
        weights = get_evaluation_weights()
        self.assertIsInstance(weights, dict)
        self.assertIn("difficulty", weights)
        total = sum(weights.values())
        self.assertAlmostEqual(total, 1.0, places=2)

    def test_get_evaluation_thresholds(self):
        thresholds = get_evaluation_thresholds()
        self.assertIsInstance(thresholds, dict)
        self.assertIn("highly_recommended", thresholds)
        self.assertIn("recommended", thresholds)


class TestValidationResult(unittest.TestCase):
    def test_initial_state(self):
        r = ValidationResult()
        self.assertTrue(r.valid)
        self.assertEqual(r.errors, [])
        self.assertEqual(r.warnings, [])

    def test_add_error(self):
        r = ValidationResult()
        r.add_error("error1")
        self.assertFalse(r.valid)
        self.assertEqual(r.errors, ["error1"])

    def test_add_warning(self):
        r = ValidationResult()
        r.add_warning("warn1")
        self.assertTrue(r.valid)
        self.assertEqual(r.warnings, ["warn1"])

    def test_multiple_errors(self):
        r = ValidationResult()
        r.add_error("e1")
        r.add_error("e2")
        self.assertEqual(len(r.errors), 2)


if __name__ == "__main__":
    unittest.main()
