"""赏金评估脚本，评估 issue 的可行性并输出评估报告"""

import json
import os
from dataclasses import dataclass, field, asdict
from datetime import datetime, timezone
from typing import Any, Optional

import yaml


@dataclass
class EvalScore:
    difficulty: float = 0.0
    amount: float = 0.0
    competition: float = 0.0
    timeliness: float = 0.0
    project_quality: float = 0.0

    @property
    def total(self) -> float:
        weights = {
            "difficulty": 0.25,
            "amount": 0.25,
            "competition": 0.15,
            "timeliness": 0.15,
            "project_quality": 0.20,
        }
        return round(
            sum(
                getattr(self, k) * w
                for k, w in weights.items()
            ),
            2,
        )


@dataclass
class EvalResult:
    repository: str
    title: str
    url: str
    scores: EvalScore
    total_score: float = 0.0
    recommendation: str = ""
    evaluated_at: str = ""
    details: dict[str, Any] = field(default_factory=dict)

    def to_dict(self) -> dict[str, Any]:
        return {
            "repository": self.repository,
            "title": self.title,
            "url": self.url,
            "scores": asdict(self.scores),
            "total_score": self.total_score,
            "recommendation": self.recommendation,
            "evaluated_at": self.evaluated_at,
            "details": self.details,
        }


DEFAULT_WEIGHTS = {
    "difficulty": 0.25,
    "amount": 0.25,
    "competition": 0.15,
    "timeliness": 0.15,
    "project_quality": 0.20,
}

DEFAULT_THRESHOLDS = {
    "highly_recommended": 7.5,
    "recommended": 6.0,
    "maybe": 4.5,
}

DIFFICULTY_KEYWORDS_EASY = [
    "fix typo",
    "typo",
    "spelling",
    "documentation",
    "update doc",
    "readme",
    "simple fix",
    "small fix",
    "minor",
    "trivial",
]

DIFFICULTY_KEYWORDS_HARD = [
    "refactor",
    "rewrite",
    "architecture",
    "design system",
    "migration",
    "performance",
    "optimization",
    "scalability",
    "security",
    "authentication",
    "authorization",
    "encryption",
    "database migration",
    "api redesign",
]


def load_filters_config(config_path: Optional[str] = None) -> dict:
    if config_path is None:
        config_path = os.path.join(
            os.path.dirname(os.path.dirname(__file__)),
            "config",
            "filters.yaml",
        )
    if not os.path.exists(config_path):
        return {}
    with open(config_path, "r", encoding="utf-8") as f:
        return yaml.safe_load(f) or {}


def evaluate_difficulty(issue: dict[str, Any]) -> float:
    title = (issue.get("title") or "").lower()
    body = (issue.get("body") or "").lower()
    combined = f"{title} {body}"

    for keyword in DIFFICULTY_KEYWORDS_HARD:
        if keyword in combined:
            max_score = 8.0
            break
    else:
        max_score = 5.0

    for keyword in DIFFICULTY_KEYWORDS_EASY:
        if keyword in combined:
            min_score = 1.0
            break
    else:
        min_score = 3.0

    labels = issue.get("labels") or []
    has_good_first_issue = any(
        "good first issue" in label.lower() or "beginner" in label.lower()
        for label in labels
    )
    has_help_wanted = any("help wanted" in label.lower() for label in labels)

    score = (min_score + max_score) / 2.0
    if has_good_first_issue:
        score = min(score, 3.0)
    if help_wanted := has_help_wanted:
        score = min(score + 1.0, 9.0)

    return _clamp(score, 0, 10)


def evaluate_amount(bounty_amount: int, currency: str = "USD") -> float:
    if bounty_amount <= 0:
        return 1.0
    if currency.upper() != "USD":
        return _clamp(min(bounty_amount / 100.0 * 5, 10), 0, 10)
    if bounty_amount < 20:
        return 2.0
    elif bounty_amount < 50:
        return 4.0
    elif bounty_amount < 100:
        return 6.0
    elif bounty_amount < 200:
        return 8.0
    else:
        return 10.0


def evaluate_competition(
    claimant_count: int = 0,
    pr_count: int = 0,
    is_claimed_by_me: bool = False,
) -> float:
    if is_claimed_by_me:
        return 8.0

    penalty = claimant_count * 2.0 + pr_count * 1.5
    score = max(10.0 - penalty, 0.0)

    if claimant_count >= 3:
        score = min(score, 2.0)
    elif claimant_count >= 2:
        score = min(score, 4.0)

    return _clamp(score, 0, 10)


def evaluate_timeliness(created_at: str, max_age_days: int = 30) -> float:
    if not created_at:
        return 5.0

    try:
        if created_at.endswith("Z"):
            created_at = created_at[:-1] + "+00:00"
        created_dt = datetime.fromisoformat(created_at)
        if created_dt.tzinfo is None:
            created_dt = created_dt.replace(tzinfo=timezone.utc)
        age_days = (datetime.now(timezone.utc) - created_dt).days
    except (ValueError, TypeError):
        return 5.0

    if age_days <= 3:
        return 10.0
    elif age_days <= 7:
        return 8.0
    elif age_days <= 14:
        return 6.0
    elif age_days <= max_age_days:
        return 4.0
    else:
        return 2.0


def evaluate_project_quality(
    stars: int = 0,
    recent_commits: int = 0,
    open_issues: int = 0,
) -> float:
    star_score = _clamp(stars / 500.0 * 5, 0, 5)
    activity_score = _clamp(recent_commits * 0.5, 0, 3)
    health_penalty = max(0, (open_issues - 100) / 100.0)

    total = star_score + activity_score + 2.0 - health_penalty
    return _clamp(total, 0, 10)


def get_recommendation(total_score: float, thresholds: Optional[dict] = None) -> str:
    t = thresholds or DEFAULT_THRESHOLDS
    if total_score >= t["highly_recommended"]:
        return "strongly_recommend"
    elif total_score >= t["recommended"]:
        return "recommend"
    elif total_score >= t["maybe"]:
        return "maybe"
    else:
        return "skip"


def evaluate_issue(
    issue: dict[str, Any],
    config: Optional[dict] = None,
) -> EvalResult:
    config = config or load_filters_config()

    difficulty = evaluate_difficulty(issue)
    amount = evaluate_amount(
        issue.get("bounty_amount", 0),
        issue.get("currency", "USD"),
    )
    competition = evaluate_competition(
        claimant_count=len(issue.get("claimants", [])),
        pr_count=issue.get("pr_count", 0),
        is_claimed_by_me=issue.get("is_claimed_by_me", False),
    )
    timeliness = evaluate_timeliness(
        issue.get("created_at", ""),
        config.get("timeliness", {}).get("max_age_days", 30),
    )
    project_quality = evaluate_project_quality(
        stars=issue.get("stars", 0),
        recent_commits=issue.get("recent_commits", 0),
        open_issues=issue.get("open_issues", 0),
    )

    scores = EvalScore(
        difficulty=round(difficulty, 2),
        amount=round(amount, 2),
        competition=round(competition, 2),
        timeliness=round(timeliness, 2),
        project_quality=round(project_quality, 2),
    )

    total = scores.total
    recommendation = get_recommendation(total, config.get("thresholds"))

    result = EvalResult(
        repository=issue.get("repository", ""),
        title=issue.get("title", ""),
        url=issue.get("url", ""),
        scores=scores,
        total_score=total,
        recommendation=recommendation,
        evaluated_at=datetime.now(timezone.utc).isoformat(),
        details={
            "bounty_amount": issue.get("bounty_amount", 0),
            "currency": issue.get("currency", "USD"),
            "claimant_count": len(issue.get("claimants", [])),
            "pr_count": issue.get("pr_count", 0),
            "labels": issue.get("labels", []),
        },
    )
    return result


def evaluate_batch(
    issues: list[dict[str, Any]],
    config: Optional[dict] = None,
) -> list[EvalResult]:
    results = []
    for issue in issues:
        try:
            result = evaluate_issue(issue, config)
            results.append(result)
        except Exception as e:
            results.append(
                EvalResult(
                    repository=issue.get("repository", "unknown"),
                    title=issue.get("title", "unknown"),
                    url=issue.get("url", ""),
                    scores=EvalScore(),
                    total_score=0.0,
                    recommendation="error",
                    details={"error": str(e)},
                )
            )
    return sorted(results, key=lambda r: r.total_score, reverse=True)


def generate_report(results: list[EvalResult]) -> dict[str, Any]:
    summary = {
        "total_evaluated": len(results),
        "strongly_recommend": sum(
            1 for r in results if r.recommendation == "strongly_recommend"
        ),
        "recommend": sum(
            1 for r in results if r.recommendation == "recommend"
        ),
        "maybe": sum(1 for r in results if r.recommendation == "maybe"),
        "skip": sum(1 for r in results if r.recommendation == "skip"),
        "average_score": (
            round(sum(r.total_score for r in results) / len(results), 2)
            if results
            else 0.0
        ),
        "top_issues": [r.to_dict() for r in results[:5]],
    }
    return {
        "report_type": "bounty_evaluation",
        "generated_at": datetime.now(timezone.utc).isoformat(),
        "summary": summary,
        "results": [r.to_dict() for r in results],
    }


def main():
    import argparse

    parser = argparse.ArgumentParser(description="GitHub Bounty Hunter - 赏金评估工具")
    parser.add_argument(
        "-i",
        "--input",
        required=True,
        help="输入 JSON 文件路径（赏金发现脚本的输出）",
    )
    parser.add_argument(
        "-o",
        "--output",
        default="",
        help="输出 JSON 报告文件路径（默认 stdout）",
    )
    parser.add_argument(
        "-c",
        "--config",
        default=None,
        help="筛选条件配置文件路径",
    )
    args = parser.parse_args()

    with open(args.input, "r", encoding="utf-8") as f:
        issues = json.load(f)

    config = load_filters_config(args.config)
    results = evaluate_batch(issues, config)
    report = generate_report(results)

    output_json = json.dumps(report, ensure_ascii=False, indent=2)

    if args.output:
        with open(args.output, "w", encoding="utf-8") as f:
            f.write(output_json)
        print(f"报告已保存到: {args.output}")
    else:
        print(output_json)

    print(f"\n=== 评估摘要 ===", file=__import__("sys").stderr)
    print(f"总评估数: {report['summary']['total_evaluated']}", file=__import__("sys").stderr)
    print(f"强烈推荐: {report['summary']['strongly_recommend']}", file=__import__("sys").stderr)
    print(f"推荐: {report['summary']['recommend']}", file=__import__("sys").stderr)
    print(f"可以考虑: {report['summary']['maybe']}", file=__import__("sys").stderr)
    print(f"跳过: {report['summary']['skip']}", file=__import__("sys").stderr)
    print(f"平均分: {report['summary']['average_score']}", file=__import__("sys").stderr)


def _clamp(value: float, min_val: float, max_val: float) -> float:
    return max(min_val, min(value, max_val))


if __name__ == "__main__":
    main()
