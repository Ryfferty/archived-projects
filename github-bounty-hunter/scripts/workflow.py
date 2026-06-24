"""GitHub Bounty Hunter 工作流集成，串联发现→评估→推荐全流程"""

import json
import logging
import os
import subprocess
import sys
from dataclasses import dataclass, field, asdict
from datetime import datetime, timezone
from pathlib import Path
from typing import Any, Optional

PROJECT_ROOT = Path(__file__).resolve().parent.parent

logger = logging.getLogger(__name__)


@dataclass
class WorkflowResult:
    discovered_count: int = 0
    evaluated_count: int = 0
    recommended: list[dict] = field(default_factory=list)
    skipped: list[dict] = field(default_factory=list)
    errors: list[str] = field(default_factory=list)
    runtime_seconds: float = 0.0
    timestamp: str = ""

    def to_dict(self) -> dict[str, Any]:
        return {
            "discovered_count": self.discovered_count,
            "evaluated_count": self.evaluated_count,
            "recommended": self.recommended,
            "skipped": self.skipped,
            "errors": self.errors,
            "runtime_seconds": round(self.runtime_seconds, 2),
            "timestamp": self.timestamp,
        }


def run_bounty_scout(
    labels: Optional[list[str]] = None,
    language: Optional[str] = None,
    min_amount: int = 0,
    limit: int = 20,
    proxy: Optional[str] = None,
) -> list[dict[str, Any]]:
    cmd = [
        str(PROJECT_ROOT / "scripts" / "bounty-scout.sh"),
        "-n", str(limit),
        "-j",
    ]

    if labels:
        cmd.extend(["-l", ",".join(labels)])
    if language:
        cmd.extend(["-L", language])
    if min_amount > 0:
        cmd.extend(["-m", str(min_amount)])
    if proxy:
        cmd.extend(["-p", proxy])

    logger.info(f"执行赏金发现: {' '.join(cmd)}")

    try:
        result = subprocess.run(
            cmd,
            capture_output=True,
            text=True,
            timeout=120,
            cwd=str(PROJECT_ROOT),
        )

        if result.returncode != 0:
            logger.error(f"赏金发现失败: {result.stderr}")
            return []

        output = result.stdout.strip()
        if not output:
            return []

        issues = json.loads(output)
        if isinstance(issues, list):
            return issues

        return []
    except subprocess.TimeoutExpired:
        logger.error("赏金发现超时")
        return []
    except json.JSONDecodeError as e:
        logger.error(f"赏金发现输出解析失败: {e}")
        return []
    except FileNotFoundError:
        logger.error("bounty-scout.sh 脚本未找到")
        return []
    except Exception as e:
        logger.error(f"赏金发现异常: {e}")
        return []


def evaluate_discovered_issues(
    issues: list[dict[str, Any]],
) -> list[dict[str, Any]]:
    from scripts.bounty_eval import evaluate_batch, generate_report

    results = evaluate_batch(issues)
    report = generate_report(results)
    return report.get("results", [])


def filter_recommendations(
    evaluated_issues: list[dict[str, Any]],
    min_score: float = 4.5,
    max_results: int = 10,
) -> tuple[list[dict], list[dict]]:
    recommended = []
    skipped = []

    for issue in evaluated_issues:
        score = issue.get("total_score", 0)
        recommendation = issue.get("recommendation", "")

        if score >= min_score and recommendation in ("strongly_recommend", "recommend"):
            recommended.append(issue)
        else:
            skipped.append(issue)

    recommended.sort(key=lambda x: x.get("total_score", 0), reverse=True)
    recommended = recommended[:max_results]

    return recommended, skipped


def run_full_workflow(
    labels: Optional[list[str]] = None,
    language: Optional[str] = None,
    min_amount: int = 0,
    limit: int = 20,
    min_score: float = 4.5,
    max_results: int = 10,
    proxy: Optional[str] = None,
    interactive: bool = False,
    output_file: Optional[str] = None,
) -> WorkflowResult:
    import time

    start_time = time.time()
    result = WorkflowResult(timestamp=datetime.now(timezone.utc).isoformat())

    logger.info("=" * 50)
    logger.info("GitHub Bounty Hunter - 完整工作流启动")
    logger.info("=" * 50)

    discovered = run_bounty_scout(
        labels=labels,
        language=language,
        min_amount=min_amount,
        limit=limit,
        proxy=proxy,
    )
    result.discovered_count = len(discovered)

    if not discovered:
        result.errors.append("未发现任何赏金 issue")
        result.runtime_seconds = time.time() - start_time
        return result

    logger.info(f"发现 {len(discovered)} 个赏金 issue")

    evaluated = evaluate_discovered_issues(discovered)
    result.evaluated_count = len(evaluated)

    logger.info(f"评估完成，共 {len(evaluated)} 个结果")

    recommended, skipped = filter_recommendations(
        evaluated_issues=evaluated,
        min_score=min_score,
        max_results=max_results,
    )
    result.recommended = [r for r in recommended]
    result.skipped = skipped[:20]

    logger.info(f"推荐: {len(recommended)} 个, 跳过: {len(skipped)} 个")

    if interactive and recommended:
        _interactive_select(recommended)

    result.runtime_seconds = time.time() - start_time

    if output_file:
        _save_report(result, output_file)

    return result


def _interactive_select(recommended: list[dict]) -> None:
    print("\n" + "=" * 60)
    print("🎯 推荐的赏金 Issue (按综合评分排序)")
    print("=" * 60)

    for i, item in enumerate(recommended):
        score = item.get("total_score", 0)
        rec = item.get("recommendation", "")
        repo = item.get("repository", "?")
        title = item.get("title", "?")
        amount = item.get("details", {}).get("bounty_amount", "?")

        rec_icon = {"strongly_recommend": "🔥", "recommend": "⭐", "maybe": "💭", "skip": "⏭️"}.get(rec, "❓")

        print(f"  [{i+1}] {rec_icon} ({score:.1f}/10) [{repo}] {title} | ~${amount}")

    print("\n输入编号查看详情 (q 退出): ", end="", flush=True)

    try:
        choice = input().strip()
        if choice.lower() == "q":
            print("退出选择模式")
            return

        idx = int(choice) - 1
        if 0 <= idx < len(recommended):
            item = recommended[idx]
            print("\n--- Issue 详情 ---")
            print(json.dumps(item, ensure_ascii=False, indent=2))
        else:
            print("无效编号")
    except (ValueError, EOFError):
        pass


def _save_report(result: WorkflowResult, output_file: str) -> None:
    report_data = {
        "report_type": "workflow_summary",
        "generated_at": result.timestamp,
        "workflow": result.to_dict(),
    }

    output_path = Path(output_file)
    output_path.parent.mkdir(parents=True, exist_ok=True)

    with open(output_path, "w", encoding="utf-8") as f:
        json.dump(report_data, f, ensure_ascii=False, indent=2)

    logger.info(f"工作报告已保存到: {output_path}")


def format_workflow_summary(result: WorkflowResult) -> str:
    lines = [
        "",
        "╔══════════════════════════════════════════════╗",
        "║   GitHub Bounty Hunter - 工作流报告           ║",
        f"║   {result.timestamp[:19]}              ║",
        "╠══════════════════════════════════════════════╣",
        f"║  发现: {result.discovered_count:>3} 个 │ "
        f"评估: {result.evaluated_count:>3} 个 │ "
        f"推荐: {len(result.recommended):>3} 个 ║",
        f"║  耗时: {result.runtime_seconds:>6.2f}s                        ║",
        "╠══════════════════════════════════════════════╣",
        "║  🏆 TOP 推荐赏金:                            ║",
        "╚══════════════════════════════════════════════╝",
    ]

    for i, item in enumerate(result.recommended[:5], 1):
        score = item.get("total_score", 0)
        title = item.get("title", "?")[:45]
        repo = item.get("repository", "?")[:18]
        rec = item.get("recommendation", "")
        icon = {"strongly_recommend": "🔥", "recommend": "⭐"}.get(rec, "•")
        lines.append(f"  {icon} #{i:>2} ({score:.1f}) [{repo}] {title}")

    if result.errors:
        lines.append("")
        lines.append("  ⚠️ 错误/警告:")
        for err in result.errors[:5]:
            lines.append(f"     - {err}")

    return "\n".join(lines)


def main():
    import argparse

    parser = argparse.ArgumentParser(
        description="GitHub Bounty Hunter - 完整工作流",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
示例:
  python scripts/workflow.py --language python --min-amount 50
  python scripts/workflow.py --interactive --limit 30
  python scripts/workflow.py -o report.json --proxy ghfast.top
        """,
    )
    parser.add_argument("-L", "--language", help="按语言筛选 (python/typescript)")
    parser.add_argument("-m", "--min-amount", type=int, default=0, help="最小赏金金额")
    parser.add_argument("-n", "--limit", type=int, default=20, help="发现数量上限")
    parser.add_argument("--min-score", type=float, default=4.5, help="最低推荐分数")
    parser.add_argument("--max-results", type=int, default=10, help="最大推荐数量")
    parser.add_argument("-p", "--proxy", help="代理地址")
    parser.add_argument("-o", "--output", default="", help="报告输出文件")
    parser.add_argument("-i", "--interactive", action="store_true", help="交互式选择模式")
    parser.add_argument("-v", "--verbose", action="store_true", help="详细日志")
    args = parser.parse_args()

    log_level = logging.DEBUG if args.verbose else logging.INFO
    logging.basicConfig(
        level=log_level,
        format="%(asctime)s [%(levelname)s] %(name)s: %(message)s",
        datefmt="%Y-%m-%d %H:%M:%S",
    )

    labels = ["bounty", "💰 Bounty", "💎 Bounty", "赏金"]

    result = run_full_workflow(
        labels=labels,
        language=args.language,
        min_amount=args.min_amount,
        limit=args.limit,
        min_score=args.min_score,
        max_results=args.max_results,
        proxy=args.proxy,
        interactive=args.interactive,
        output_file=args.output or None,
    )

    summary = format_workflow_summary(result)
    print(summary)

    if not args.output:
        print("\n--- JSON 报告 ---")
        print(json.dumps(result.to_dict(), ensure_ascii=False, indent=2))

    if result.errors:
        for err in result.errors:
            print(f"[ERROR] {err}", file=sys.stderr)

    return 0 if result.recommended else 1


if __name__ == "__main__":
    sys.exit(main())
