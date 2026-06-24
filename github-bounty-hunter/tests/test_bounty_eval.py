"""赏金评估脚本单元测试"""

import json
import os
import sys
import unittest
from datetime import datetime, timezone, timedelta
from unittest.mock import patch, MagicMock

sys.path.insert(0, os.path.join(os.path.dirname(__file__), ".."))

from scripts.bounty_eval import (
    DEFAULT_THRESHOLDS,
    DEFAULT_WEIGHTS,
    EvalResult,
    EvalScore,
    evaluate_amount,
    evaluate_competition,
    evaluate_difficulty,
    evaluate_issue,
    evaluate_project_quality,
    evaluate_timeliness,
    evaluate_batch,
    generate_report,
    get_recommendation,
    load_filters_config,
)


class TestEvaluateDifficulty(unittest.TestCase):
    def test_easy_issue(self):
        issue = {"title": "Fix typo in README", "body": "There is a spelling mistake", "labels": []}
        score = evaluate_difficulty(issue)
        self.assertLessEqual(score, 3.0)

    def test_hard_issue(self):
        issue = {
            "title": "Refactor authentication system",
            "body": "Need to redesign the entire auth architecture",
            "labels": [],
        }
        score = evaluate_difficulty(issue)
        self.assertGreater(score, 5.0)

    def test_good_first_issue_label(self):
        issue = {
            "title": "Add error handling",
            "body": "",
            "labels": ["good first issue", "bug"],
        }
        score = evaluate_difficulty(issue)
        self.assertLessEqual(score, 3.0)

    def test_help_wanted_label(self):
        issue = {
            "title": "Implement feature X",
            "body": "",
            "labels": ["help wanted"],
        }
        score = evaluate_difficulty(issue)
        self.assertGreater(score, 3.0)

    def test_no_labels_or_keywords(self):
        issue = {
            "title": "Fix bug in module",
            "body": "Something is broken",
            "labels": [],
        }
        score = evaluate_difficulty(issue)
        self.assertGreaterEqual(score, 0.0)
        self.assertLessEqual(score, 10.0)

    def test_body_contains_easy_keyword(self):
        issue = {
            "title": "Update docs",
            "body": "This is a documentation update task",
            "labels": [],
        }
        score = evaluate_difficulty(issue)
        self.assertLess(score, 4.0)

    def test_score_range(self):
        for title in ["a", "refactor everything", "fix typo"]:
            for body in ["", "complex stuff"]:
                score = evaluate_difficulty({"title": title, "body": body, "labels": []})
                self.assertGreaterEqual(score, 0.0)
                self.assertLessEqual(score, 10.0)


class TestEvaluateAmount(unittest.TestCase):
    def test_zero_bounty(self):
        self.assertEqual(evaluate_amount(0), 1.0)

    def test_small_bounty(self):
        self.assertEqual(evaluate_amount(15), 2.0)

    def test_medium_bounty_50(self):
        self.assertEqual(evaluate_amount(50), 6.0)

    def test_medium_bounty_100(self):
        self.assertEqual(evaluate_amount(100), 8.0)

    def test_large_bounty(self):
        self.assertEqual(evaluate_amount(200), 10.0)

    def test_very_large_bounty(self):
        self.assertEqual(evaluate_amount(500), 10.0)

    def test_non_usd_currency(self):
        score = evaluate_amount(5000, currency="JPY")
        self.assertEqual(score, 10.0)

    def test_small_non_usd_currency(self):
        score = evaluate_amount(50, currency="EUR")
        expected = min(50 / 100.0 * 5, 10)
        self.assertAlmostEqual(score, expected, places=1)


class TestEvaluateCompetition(unittest.TestCase):
    def test_no_competition(self):
        score = evaluate_competition()
        self.assertEqual(score, 10.0)

    def test_one_claimant(self):
        score = evaluate_competition(claimant_count=1)
        self.assertAlmostEqual(score, 8.0, places=1)

    def test_many_claimants(self):
        score = evaluate_competition(claimant_count=5)
        self.assertLessEqual(score, 2.0)

    def test_with_prs(self):
        score = evaluate_competition(pr_count=3)
        self.assertAlmostEqual(score, 5.5, places=1)

    def test_claimed_by_me(self):
        score = evaluate_competition(is_claimed_by_me=True)
        self.assertEqual(score, 8.0)

    def test_combined_penalty(self):
        score = evaluate_competition(claimant_count=2, pr_count=2)
        expected = max(10.0 - (2 * 2.0 + 2 * 1.5), 0.0)
        self.assertAlmostEqual(score, expected, places=1)

    def test_score_range(self):
        for claimants in range(6):
            for prs in range(5):
                score = evaluate_competition(claimant_count=claimants, pr_count=prs)
                self.assertGreaterEqual(score, 0.0)
                self.assertLessEqual(score, 10.0)


class TestEvaluateTimeliness(unittest.TestCase):
    def test_new_issue_today(self):
        created = (datetime.now(timezone.utc) - timedelta(hours=2)).isoformat()
        score = evaluate_timeliness(created)
        self.assertEqual(score, 10.0)

    def test_issue_one_week_old(self):
        created = (datetime.now(timezone.utc) - timedelta(days=5)).isoformat()
        score = evaluate_timeliness(created)
        self.assertEqual(score, 8.0)

    def test_issue_two_weeks_old(self):
        created = (datetime.now(timezone.utc) - timedelta(days=10)).isoformat()
        score = evaluate_timeliness(created)
        self.assertEqual(score, 6.0)

    def test_old_issue(self):
        created = (datetime.now(timezone.utc) - timedelta(days=25)).isoformat()
        score = evaluate_timeliness(created)
        self.assertEqual(score, 4.0)

    def test_very_old_issue(self):
        created = (datetime.now(timezone.utc) - timedelta(days=60)).isoformat()
        score = evaluate_timeliness(created)
        self.assertEqual(score, 2.0)

    def test_invalid_date_format(self):
        score = evaluate_timeliness("not-a-date")
        self.assertEqual(score, 5.0)

    def test_empty_date(self):
        score = evaluate_timeliness("")
        self.assertEqual(score, 5.0)

    def test_iso_with_z_suffix(self):
        created = (datetime.now(timezone.utc) - timedelta(days=1)).strftime("%Y-%m-%dT%H:%M:%SZ")
        score = evaluate_timeliness(created)
        self.assertEqual(score, 10.0)

    def test_custom_max_age(self):
        created = (datetime.now(timezone.utc) - timedelta(days=20)).isoformat()
        score = evaluate_timeliness(created, max_age_days=14)
        self.assertEqual(score, 2.0)


class TestEvaluateProjectQuality(unittest.TestCase):
    def test_popular_active_repo(self):
        score = evaluate_project_quality(stars=5000, recent_commits=50)
        self.assertGreater(score, 7.0)

    def test_small_inactive_repo(self):
        score = evaluate_project_quality(stars=10, recent_commits=0)
        self.assertLess(score, 5.0)

    def test_many_open_issues_penalty(self):
        score_normal = evaluate_project_quality(stars=1000, recent_commits=20, open_issues=50)
        score_penalty = evaluate_project_quality(stars=1000, recent_commits=20, open_issues=200)
        self.assertLess(score_penalty, score_normal)

    def test_zero_values(self):
        score = evaluate_project_quality()
        self.assertGreaterEqual(score, 0.0)
        self.assertLessEqual(score, 10.0)

    def test_max_stars(self):
        score = evaluate_project_quality(stars=10000, recent_commits=100)
        self.assertLessEqual(score, 10.0)


class TestEvalScore(unittest.TestCase):
    def test_default_scores(self):
        score = EvalScore()
        self.assertEqual(score.difficulty, 0.0)
        self.assertEqual(score.total, 0.0)

    def test_total_calculation(self):
        score = EvalScore(
            difficulty=8.0,
            amount=7.0,
            competition=6.0,
            timeliness=9.0,
            project_quality=8.0,
        )
        expected = (
            8.0 * 0.25
            + 7.0 * 0.25
            + 6.0 * 0.15
            + 9.0 * 0.15
            + 8.0 * 0.20
        )
        self.assertAlmostEqual(score.total, round(expected, 2))

    def test_max_scores(self):
        score = EvalScore(10.0, 10.0, 10.0, 10.0, 10.0)
        self.assertEqual(score.total, 10.0)


class TestGetRecommendation(unittest.TestCase):
    def test_strongly_recommend(self):
        rec = get_recommendation(8.5)
        self.assertEqual(rec, "strongly_recommend")

    def test_recommend(self):
        rec = get_recommendation(7.0)
        self.assertEqual(rec, "recommend")

    def test_maybe(self):
        rec = get_recommendation(5.0)
        self.assertEqual(rec, "maybe")

    def test_skip(self):
        rec = get_recommendation(3.0)
        self.assertEqual(rec, "skip")

    def test_boundary_highly_recommended(self):
        rec = get_recommendation(7.5)
        self.assertEqual(rec, "strongly_recommend")

    def test_custom_thresholds(self):
        rec = get_recommendation(5.0, thresholds={"highly_recommended": 3, "recommended": 2, "maybe": 1})
        self.assertEqual(rec, "strongly_recommend")


class TestEvaluateIssue(unittest.TestCase):
    def setUp(self):
        self.sample_issue = {
            "repository": "owner/repo",
            "title": "Fix login bug",
            "url": "https://github.com/owner/repo/issues/42",
            "body": "Login fails with OAuth",
            "bounty_amount": 100,
            "currency": "USD",
            "created_at": (datetime.now(timezone.utc) - timedelta(days=5)).isoformat(),
            "labels": ["bug", "bounty"],
            "claimants": [],
            "pr_count": 0,
            "is_claimed_by_me": False,
            "stars": 500,
            "recent_commits": 10,
            "open_issues": 30,
        }

    def test_full_evaluation(self):
        result = evaluate_issue(self.sample_issue)
        self.assertIsInstance(result, EvalResult)
        self.assertEqual(result.repository, "owner/repo")
        self.assertEqual(result.title, "Fix login bug")
        self.assertGreater(result.total_score, 0.0)
        self.assertIn(result.recommendation, [
            "strongly_recommend",
            "recommend",
            "maybe",
            "skip",
        ])

    def test_result_has_all_fields(self):
        result = evaluate_issue(self.sample_issue)
        self.assertIsNotNone(result.evaluated_at)
        self.assertIsInstance(result.scores, EvalScore)
        self.assertIsInstance(result.details, dict)

    def test_high_value_issue(self):
        self.sample_issue["bounty_amount"] = 300
        self.sample_issue["stars"] = 5000
        self.sample_issue["claimants"] = []
        result = evaluate_issue(self.sample_issue)
        self.assertGreater(result.scores.amount, 8.0)

    def test_low_value_competitive_issue(self):
        self.sample_issue["bounty_amount"] = 10
        self.sample_issue["claimants"] = ["dev1", "dev2", "dev3"]
        self.sample_issue["pr_count"] = 3
        result = evaluate_issue(self.sample_issue)
        self.assertLess(result.scores.competition, 4.0)


class TestEvaluateBatch(unittest.TestCase):
    def test_batch_empty(self):
        results = evaluate_batch([])
        self.assertEqual(results, [])

    def test_batch_multiple_issues(self):
        issues = [
            {
                "repository": "org/a",
                "title": "Issue A",
                "url": "https://example.com/a",
                "bounty_amount": 100,
                "currency": "USD",
                "created_at": (datetime.now(timezone.utc) - timedelta(days=2)).isoformat(),
                "labels": [],
                "claimants": [],
                "pr_count": 0,
                "is_claimed_by_me": False,
                "stars": 1000,
                "recent_commits": 20,
                "open_issues": 10,
            },
            {
                "repository": "org/b",
                "title": "Issue B",
                "url": "https://example.com/b",
                "bounty_amount": 200,
                "currency": "USD",
                "created_at": (datetime.now(timezone.utc) - timedelta(days=1)).isoformat(),
                "labels": ["good first issue"],
                "claimants": [],
                "pr_count": 0,
                "is_claimed_by_me": False,
                "stars": 2000,
                "recent_commits": 30,
                "open_issues": 5,
            },
        ]
        results = evaluate_batch(issues)
        self.assertEqual(len(results), 2)
        self.assertGreaterEqual(results[0].total_score, results[1].total_score)

    def test_batch_handles_error(self):
        bad_issue = {"bad_key": "value"}
        results = evaluate_batch([bad_issue])
        self.assertEqual(len(results), 1)
        self.assertIsInstance(results[0], EvalResult)


class TestGenerateReport(unittest.TestCase):
    def test_report_structure(self):
        results = [
            EvalResult(
                repository="org/repo",
                title="Test Issue",
                url="https://example.com/1",
                scores=EvalScore(difficulty=5.0, amount=8.0),
                total_score=6.5,
                recommendation="recommend",
                evaluated_at=datetime.now(timezone.utc).isoformat(),
            )
        ]
        report = generate_report(results)
        self.assertEqual(report["report_type"], "bounty_evaluation")
        self.assertIn("summary", report)
        self.assertIn("results", report)
        self.assertEqual(report["summary"]["total_evaluated"], 1)
        self.assertEqual(report["summary"]["recommend"], 1)

    def test_report_empty(self):
        report = generate_report([])
        self.assertEqual(report["summary"]["total_evaluated"], 0)
        self.assertEqual(report["summary"]["average_score"], 0.0)

    def test_top_issues_limit(self):
        results = [
            EvalResult(
                repository=f"org/repo{i}",
                title=f"Issue {i}",
                url=f"https://example.com/{i}",
                scores=EvalScore(),
                total_score=float(i),
                evaluated_at="",
            )
            for i in range(10)
        ]
        report = generate_report(results)
        self.assertEqual(len(report["summary"]["top_issues"]), 5)


class TestLoadFiltersConfig(unittest.TestCase):
    def test_load_existing_config(self):
        config_path = os.path.join(
            os.path.dirname(__file__), "..", "config", "filters.yaml"
        )
        if os.path.exists(config_path):
            config = load_filters_config(config_path)
            self.assertIsInstance(config, dict)

    def test_load_missing_config(self):
        config = load_filters_config("/nonexistent/path.yaml")
        self.assertEqual(config, {})


if __name__ == "__main__":
    unittest.main()
