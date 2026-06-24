"""工作流集成单元测试"""

import json
import os
import sys
import unittest
from datetime import datetime, timezone
from unittest.mock import MagicMock, patch, call

sys.path.insert(0, os.path.join(os.path.dirname(__file__), ".."))

from scripts.workflow import (
    WorkflowResult,
    _interactive_select,
    _save_report,
    filter_recommendations,
    format_workflow_summary,
    run_bounty_scout,
    run_full_workflow,
)


class TestRunBountyScout(unittest.TestCase):
    @patch("scripts.workflow.subprocess.run")
    def test_successful_run(self, mock_run):
        mock_result = MagicMock()
        mock_result.returncode = 0
        mock_result.stdout = json.dumps([
            {"repository": "org/repo", "title": "Test issue", "url": "https://example.com/1"},
            {"repository": "org/repo2", "title": "Another", "url": "https://example.com/2"},
        ])
        mock_run.return_value = mock_result

        result = run_bounty_scout(limit=10)
        self.assertEqual(len(result), 2)
        self.assertEqual(result[0]["title"], "Test issue")

    @patch("scripts.workflow.subprocess.run")
    def test_with_language_filter(self, mock_run):
        mock_result = MagicMock()
        mock_result.returncode = 0
        mock_result.stdout = json.dumps([])
        mock_run.return_value = mock_result

        run_bounty_scout(language="python", min_amount=50)
        cmd_args = mock_run.call_args[0][0]
        self.assertIn("-L", cmd_args)
        idx = cmd_args.index("-L")
        self.assertEqual(cmd_args[idx + 1], "python")

    @patch("scripts.workflow.subprocess.run")
    def test_empty_output(self, mock_run):
        mock_result = MagicMock()
        mock_result.returncode = 0
        mock_result.stdout = ""
        mock_run.return_value = mock_result

        result = run_bounty_scout()
        self.assertEqual(result, [])

    @patch("scripts.workflow.subprocess.run")
    def test_nonzero_exit(self, mock_run):
        mock_result = MagicMock()
        mock_result.returncode = 1
        mock_result.stderr = "auth error"
        mock_run.return_value = mock_result

        result = run_bounty_scout()
        self.assertEqual(result, [])

    @patch("scripts.workflow.subprocess.run")
    def test_invalid_json_output(self, mock_run):
        mock_result = MagicMock()
        mock_result.returncode = 0
        mock_result.stdout = "not json {{{"
        mock_run.return_value = mock_result

        result = run_bounty_scout()
        self.assertEqual(result, [])

    @patch("scripts.workflow.subprocess.run")
    def test_script_not_found(self, mock_run):
        mock_run.side_effect = FileNotFoundError("script missing")
        result = run_bounty_scout()
        self.assertEqual(result, [])

    @patch("scripts.workflow.subprocess.run")
    def test_timeout(self, mock_run):
        import subprocess
        mock_run.side_effect = subprocess.TimeoutExpired(cmd="scout", timeout=120)
        result = run_bounty_scout()
        self.assertEqual(result, [])


class TestFilterRecommendations(unittest.TestCase):
    def test_filter_by_score(self):
        issues = [
            {"total_score": 8.5, "recommendation": "strongly_recommend"},
            {"total_score": 7.0, "recommendation": "recommend"},
            {"total_score": 3.0, "recommendation": "skip"},
            {"total_score": 5.0, "recommendation": "maybe"},
        ]
        rec, skip = filter_recommendations(issues, min_score=4.5)
        self.assertEqual(len(rec), 2)
        self.assertEqual(len(skip), 2)
        self.assertEqual(rec[0]["total_score"], 8.5)

    def test_max_results_limit(self):
        issues = [
            {"total_score": float(i), "recommendation": "recommend"}
            for i in range(10, 0, -1)
        ]
        rec, skip = filter_recommendations(issues, max_results=3)
        self.assertEqual(len(rec), 3)
        self.assertEqual(rec[0]["total_score"], 10.0)

    def test_all_skipped(self):
        issues = [
            {"total_score": 2.0, "recommendation": "skip"},
            {"total_score": 1.0, "recommendation": "skip"},
        ]
        rec, skip = filter_recommendations(issues)
        self.assertEqual(len(rec), 0)
        self.assertEqual(len(skip), 2)

    def test_maybe_not_included(self):
        issues = [
            {"total_score": 5.0, "recommendation": "maybe"},
            {"total_score": 8.0, "recommendation": "strongly_recommend"},
        ]
        rec, skip = filter_recommendations(issues)
        self.assertEqual(len(rec), 1)
        self.assertEqual(rec[0]["recommendation"], "strongly_recommend")

    def test_empty_input(self):
        rec, skip = filter_recommendations([])
        self.assertEqual(rec, [])
        self.assertEqual(skip, [])


class TestWorkflowResult(unittest.TestCase):
    def test_default_values(self):
        r = WorkflowResult()
        self.assertEqual(r.discovered_count, 0)
        self.assertEqual(r.recommended, [])
        self.assertEqual(r.errors, [])

    def test_to_dict(self):
        r = WorkflowResult(
            discovered_count=10,
            evaluated_count=10,
            recommended=[{"test": True}],
            errors=["err1"],
            runtime_seconds=5.5,
            timestamp="2024-01-01T00:00:00Z",
        )
        d = r.to_dict()
        self.assertEqual(d["discovered_count"], 10)
        self.assertEqual(d["errors"], ["err1"])
        self.assertIn("recommended", d)


class TestFormatWorkflowSummary(unittest.TestCase):
    def test_basic_summary(self):
        result = WorkflowResult(
            discovered_count=20,
            evaluated_count=18,
            recommended=[
                {
                    "total_score": 9.0,
                    "repository": "cool/project",
                    "title": "Fix important bug",
                    "recommendation": "strongly_recommend",
                },
                {
                    "total_score": 7.5,
                    "repository": "other/repo",
                    "title": "Add feature",
                    "recommendation": "recommend",
                },
            ],
            runtime_seconds=12.34,
            timestamp="2024-06-15T10:30:00Z",
        )
        summary = format_workflow_summary(result)
        self.assertIn("GitHub Bounty Hunter", summary)
        self.assertIn("20", summary)
        self.assertIn("18", summary)
        self.assertIn("12.34", summary)
        self.assertIn("Fix important bug", summary)

    def test_empty_result(self):
        result = WorkflowResult(timestamp="2024-01-01T00:00:00Z")
        summary = format_workflow_summary(result)
        self.assertIn("GitHub Bounty Hunter", summary)

    def test_with_errors(self):
        result = WorkflowResult(
            errors=["API rate limited", "Network error"],
            timestamp="2024-01-01T00:00:00Z",
        )
        summary = format_workflow_summary(result)
        self.assertIn("API rate limited", summary)


class TestSaveReport(unittest.TestCase):
    def test_save_creates_file(self):
        import tempfile

        result = WorkflowResult(
            discovered_count=5,
            recommended=[{"score": 8}],
            timestamp="2024-01-01T00:00:00Z",
        )

        with tempfile.NamedTemporaryFile(suffix=".json", delete=False) as f:
            tmp_path = f.name

        try:
            _save_report(result, tmp_path)
            with open(tmp_path, "r") as f:
                data = json.load(f)
            self.assertEqual(data["report_type"], "workflow_summary")
            self.assertEqual(data["workflow"]["discovered_count"], 5)
        finally:
            if os.path.exists(tmp_path):
                os.unlink(tmp_path)

    def test_save_creates_parent_dirs(self):
        import tempfile, shutil

        tmpdir = tempfile.mkdtemp()
        path = os.path.join(tmpdir, "sub", "dir", "report.json")
        result = WorkflowResult(timestamp="2024-01-01T00:00:00Z")

        try:
            _save_report(result, path)
            self.assertTrue(os.path.exists(path))
        finally:
            shutil.rmtree(tmpdir)


class TestRunFullWorkflow(unittest.TestCase):
    @patch("scripts.workflow._save_report")
    @patch("scripts.workflow._interactive_select")
    @patch("scripts.workflow.evaluate_discovered_issues")
    @patch("scripts.workflow.run_bounty_scout")
    def test_full_workflow_success(self, mock_scout, mock_eval, mock_interact, mock_save):
        mock_scout.return_value = [
            {"repository": "org/a", "title": "Issue A", "url": "https://a/1",
             "bounty_amount": 100, "currency": "USD",
             "created_at": "2024-06-01T00:00:00Z",
             "labels": [], "claimants": [], "pr_count": 0,
             "is_claimed_by_me": False, "stars": 500, "recent_commits": 10, "open_issues": 20},
        ]

        mock_eval.return_value = [
            {"repository": "org/a", "title": "Issue A", "url": "https://a/1",
             "scores": {"difficulty": 5, "amount": 6, "competition": 10, "timeliness": 8, "project_quality": 7},
             "total_score": 6.75, "recommendation": "recommend", "evaluated_at": "2024-06-15T00:00:00Z",
             "details": {}},
        ]

        result = run_full_workflow(interactive=False)

        self.assertEqual(result.discovered_count, 1)
        self.assertEqual(result.evaluated_count, 1)
        self.assertGreater(result.runtime_seconds, 0)
        self.assertFalse(mock_interact.called)

    @patch("scripts.workflow._save_report")
    @patch("scripts.workflow._interactive_select")
    @patch("scripts.workflow.evaluate_discovered_issues")
    @patch("scripts.workflow.run_bounty_scout")
    def test_no_discoveries(self, mock_scout, mock_eval, mock_interact, mock_save):
        mock_scout.return_value = []

        result = run_full_workflow(interactive=False)

        self.assertEqual(result.discovered_count, 0)
        self.assertTrue(any("未发现" in e for e in result.errors))

    @patch("scripts.workflow._save_report")
    @patch("scripts.workflow._interactive_select")
    @patch("scripts.workflow.evaluate_discovered_issues")
    @patch("scripts.workflow.run_bounty_scout")
    def test_with_output_file(self, mock_scout, mock_eval, mock_interact, mock_save):
        mock_scout.return_value = [
            {"repository": "org/a", "title": "Issue A", "url": "https://a/1",
             "bounty_amount": 50, "currency": "USD",
             "created_at": "2024-06-01T00:00:00Z",
             "labels": [], "claimants": [], "pr_count": 0,
             "is_claimed_by_me": False, "stars": 100, "recent_commits": 5, "open_issues": 10},
        ]
        mock_eval.return_value = [
            {"repository": "org/a", "total_score": 5.0, "recommendation": "recommend",
             "evaluated_at": "2024-06-15T00:00:00Z", "details": {}},
        ]
        run_full_workflow(output_file="/tmp/test_report.json")
        mock_save.assert_called_once()

    @patch("scripts.workflow._save_report")
    @patch("scripts.workflow._interactive_select")
    @patch("scripts.workflow.evaluate_discovered_issues")
    @patch("scripts.workflow.run_bounty_scout")
    def test_interactive_mode_calls_select(self, mock_scout, mock_eval, mock_interact, mock_save):
        mock_scout.return_value = [
            {"repository": "org/a", "title": "Issue A", "url": "https://a/1",
             "bounty_amount": 100, "currency": "USD",
             "created_at": "2024-06-01T00:00:00Z",
             "labels": [], "claimants": [], "pr_count": 0,
             "is_claimed_by_me": False, "stars": 1000, "recent_commits": 50, "open_issues": 10},
        ]
        mock_eval.return_value = [
            {"repository": "org/a", "title": "Issue A", "url": "https://a/1",
             "scores": {"difficulty": 4, "amount": 8, "competition": 10, "timeliness": 9, "project_quality": 9},
             "total_score": 7.65, "recommendation": "recommend", "evaluated_at": "2024-06-15T00:00:00Z",
             "details": {}},
        ]

        run_full_workflow(interactive=True)
        mock_interact.assert_called_once()


if __name__ == "__main__":
    unittest.main()
