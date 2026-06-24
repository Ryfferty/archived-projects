"""多平台聚合器单元测试"""

import json
import os
import sys
import unittest
from unittest.mock import MagicMock, patch, PropertyMock

sys.path.insert(0, os.path.join(os.path.dirname(__file__), ".."))

from scripts.platform_aggregator import (
    AlgoraPlatformAdapter,
    AggregatedResult,
    GitHubPlatformAdapter,
    IssueHuntPlatformAdapter,
    PlatformAggregator,
    SearchResult,
    create_aggregator,
    quick_search,
)


class TestSearchResult(unittest.TestCase):
    def test_default_values(self):
        r = SearchResult(platform="test", title="T", url="https://x.com", repository="org/repo")
        self.assertEqual(r.bounty_amount, 0)
        self.assertEqual(r.currency, "USD")
        self.assertEqual(r.state, "open")

    def test_to_dict(self):
        r = SearchResult(
            platform="github",
            title="Fix bug",
            url="https://github.com/o/r/1",
            repository="o/r",
            bounty_amount=100,
            labels=["bounty"],
            created_at="2024-01-01",
            state="open",
        )
        d = r.to_dict()
        self.assertEqual(d["platform"], "github")
        self.assertEqual(d["bounty_amount"], 100)
        self.assertIn("bounty", d["labels"])
        self.assertNotIn("raw_data", d)


class TestGitHubPlatformAdapter(unittest.TestCase):
    @patch("scripts.platform_aggregator.subprocess.run")
    def test_is_available_with_gh(self, mock_run):
        mock_run.return_value = MagicMock(returncode=0)
        adapter = GitHubPlatformAdapter()
        self.assertTrue(adapter.is_available())

    @patch("scripts.platform_aggregator.subprocess.run")
    def test_is_available_without_gh(self, mock_run):
        mock_run.side_effect = FileNotFoundError()
        adapter = GitHubPlatformAdapter()
        self.assertFalse(adapter.is_available())

    def test_name(self):
        adapter = GitHubPlatformAdapter()
        self.assertEqual(adapter.name(), "github")

    @patch("scripts.platform_aggregator.subprocess.run")
    def test_search_success(self, mock_run):
        mock_result = MagicMock(returncode=0)
        mock_result.stdout = json.dumps([
            {
                "repository": {"nameWithOwner": "owner/repo"},
                "title": "Test issue",
                "url": "https://github.com/owner/repo/issues/1",
                "labels": [{"name": "bounty"}],
                "createdAt": "2024-06-01T00:00:00Z",
                "state": "OPEN",
                "number": 1,
            },
        ])
        mock_run.return_value = mock_result

        adapter = GitHubPlatformAdapter()
        results = adapter.search(limit=10)

        self.assertEqual(len(results), 1)
        self.assertEqual(results[0].platform, "github")
        self.assertEqual(results[0].title, "Test issue")
        self.assertEqual(results[0].repository, "owner/repo")

    @patch("scripts.platform_aggregator.subprocess.run")
    def test_search_empty(self, mock_run):
        mock_result = MagicMock(returncode=0)
        mock_result.stdout = "[]"
        mock_run.return_value = mock_result

        adapter = GitHubPlatformAdapter()
        results = adapter.search()
        self.assertEqual(results, [])

    @patch("scripts.platform_aggregator.subprocess.run")
    def test_search_nonzero_exit(self, mock_run):
        mock_run.return_value = MagicMock(returncode=1, stderr="error")

        adapter = GitHubPlatformAdapter()
        results = adapter.search()
        self.assertEqual(results, [])

    @patch("scripts.platform_aggregator.subprocess.run")
    def test_search_timeout(self, mock_run):
        import subprocess
        mock_run.side_effect = subprocess.TimeoutExpired(cmd="gh", timeout=120)

        adapter = GitHubPlatformAdapter()
        results = adapter.search()
        self.assertEqual(results, [])


class TestIssueHuntPlatformAdapter(unittest.TestCase):
    def test_name(self):
        adapter = IssueHuntPlatformAdapter(token="test_token")
        self.assertEqual(adapter.name(), "issuehunt")

    def test_is_available_no_token(self):
        with patch.dict(os.environ, {}, clear=True):
            adapter = IssueHuntPlatformAdapter()
            self.assertFalse(adapter.is_available())

    def test_is_available_with_token_and_health_ok(self,):
        adapter = IssueHuntPlatformAdapter(token="tok")
        with patch.object(adapter, "_get_client") as mock_client:
            mock_client.return_value.health_check.return_value = True
            self.assertTrue(adapter.is_available())

    def test_is_available_health_fail(self,):
        adapter = IssueHuntPlatformAdapter(token="tok")
        with patch.object(adapter, "_get_client") as mock_client:
            mock_client.return_value.health_check.return_value = False
            self.assertFalse(adapter.is_available())

    @patch("scripts.platform_aggregator.IssueHuntPlatformAdapter._get_client")
    def test_search_success(self, mock_get_client):
        mock_instance = MagicMock()
        mock_instance.search_issues.return_value = [
            {
                "title": "Bounty issue",
                "url": "https://issuehunt.io/r/1",
                "repository": "org/repo",
                "bounty_amount": 100,
                "currency": "USD",
                "labels": ["bounty"],
                "created_at": "2024-06-01T00:00:00Z",
                "state": "open",
            }
        ]
        mock_get_client.return_value = mock_instance

        adapter = IssueHuntPlatformAdapter(token="tok")
        results = adapter.search(limit=5)

        self.assertEqual(len(results), 1)
        self.assertEqual(results[0].platform, "issuehunt")
        self.assertEqual(results[0].bounty_amount, 100)


class TestAlgoraPlatformAdapter(unittest.TestCase):
    def test_name(self):
        adapter = AlgoraPlatformAdapter()
        self.assertEqual(adapter.name(), "algora")

    def test_is_available_no_token(self):
        with patch.dict(os.environ, {}, clear=True):
            adapter = AlgoraPlatformAdapter()
            self.assertFalse(adapter.is_available())

    def test_is_available_with_token(self):
        adapter = AlgoraPlatformAdapter(token="key123")
        self.assertTrue(adapter.is_available())

    def test_search_success(self):
        adapter = AlgoraPlatformAdapter(token="key")
        self.assertTrue(adapter.is_available())
        self.assertEqual(adapter.name(), "algora")

    @patch("scripts.platform_aggregator.requests.get")
    def test_search_exception(self, mock_get):
        mock_get.side_effect = Exception("network error")
        adapter = AlgoraPlatformAdapter(token="key")
        results = adapter.search()
        self.assertEqual(results, [])


class TestPlatformAggregator(unittest.TestCase):
    def test_register(self):
        agg = PlatformAggregator()
        mock_adapter = MagicMock(spec=["name", "search", "is_available"])
        mock_adapter.name.return_value = "mock"

        result = agg.register(mock_adapter)
        self.assertIs(result, agg)
        self.assertEqual(len(agg._adapters), 1)

    def test_register_all(self):
        with patch.dict(os.environ, {"ISSUEHUNT_TOKEN": "t"}, clear=False):
            with patch.dict(os.environ, {"ALGORA_TOKEN": "a"}, clear=False):
                agg = PlatformAggregator().register_all()
                self.assertGreaterEqual(len(agg._adapters), 2)

    def test_search_all_empty(self):
        agg = PlatformAggregator()
        result = agg.search_all()
        self.assertEqual(result.total_results, 0)
        self.assertIsInstance(result.by_platform, dict)

    @patch("scripts.platform_aggregator.GitHubPlatformAdapter")
    def test_search_all_with_results(self, MockGHAdapter):
        mock_adapter = MagicMock()
        mock_adapter.name.return_value = "github"
        mock_adapter.is_available.return_value = True
        mock_adapter.search.return_value = [
            SearchResult(
                platform="github", title="A", url="http://a.com/1",
                repository="o/r", bounty_amount=50,
            ),
            SearchResult(
                platform="github", title="B", url="http://b.com/2",
                repository="o/r2", bounty_amount=100,
            ),
        ]
        MockGHAdapter.return_value = mock_adapter

        agg = PlatformAggregator()
        agg.register(mock_adapter)
        result = agg.search_all(deduplicate=True)

        self.assertEqual(result.total_results, 2)
        self.assertEqual(result.by_platform["github"], 2)
        self.assertGreater(result.duration_seconds, 0)

    def test_deduplication(self):
        agg = PlatformAggregator()
        mock_adapter = MagicMock()
        mock_adapter.name.return_value = "p1"
        mock_adapter.is_available.return_value = True
        mock_adapter.search.return_value = [
            SearchResult(platform="p1", title="Dup", url="http://same.url/1", repository="o/r"),
            SearchResult(platform="p1", title="Dup2", url="http://same.url/1", repository="o/r"),
        ]

        agg.register(mock_adapter)
        result = agg.search_all(deduplicate=True)
        self.assertEqual(result.total_results, 1)

        result_no_dedup = agg.search_all(deduplicate=False)
        self.assertEqual(result_no_dedup.total_results, 2)

    def test_sort_by_bounty_amount(self):
        agg = PlatformAggregator()
        mock_adapter = MagicMock()
        mock_adapter.name.return_value = "p1"
        mock_adapter.is_available.return_value = True
        mock_adapter.search.return_value = [
            SearchResult(platform="p1", title="Low", url="http://a.com/1", repository="o/r", bounty_amount=10),
            SearchResult(platform="p1", title="High", url="http://b.com/2", repository="o/r", bounty_amount=200),
        ]

        agg.register(mock_adapter)
        result = agg.search_all()
        self.assertEqual(result.results[0].bounty_amount, 200)
        self.assertEqual(result.results[1].bounty_amount, 10)

    def test_unavailable_platform_skipped(self):
        agg = PlatformAggregator()
        mock_adapter = MagicMock()
        mock_adapter.name.return_value = "down"
        mock_adapter.is_available.return_value = False

        agg.register(mock_adapter)
        result = agg.search_all()
        self.assertEqual(result.total_results, 0)
        self.assertNotIn("down", result.by_platform)

    def test_to_dict(self):
        result = AggregatedResult(
            total_results=3,
            by_platform={"github": 2, "issuehunt": 1},
            results=[
                SearchResult(platform="g", title="T", url="u", repository="r"),
            ],
            errors=["test error"],
            duration_seconds=1.5,
        )
        d = result.to_dict()
        self.assertEqual(d["total_results"], 3)
        self.assertEqual(len(d["results"]), 1)
        self.assertEqual(d["errors"], ["test error"])


class TestCreateAggregatorAndQuickSearch(unittest.TestCase):
    def test_create_aggregator_returns_type(self):
        agg = create_aggregator()
        self.assertIsInstance(agg, PlatformAggregator)

    @patch("scripts.platform_aggregator.PlatformAggregator.search_all")
    def test_quick_search_calls_search_all(self, mock_search):
        mock_search.return_value = AggregatedResult()
        result = quick_search(keyword="python", min_amount=50)
        mock_search.assert_called_once()
        call_kwargs = mock_search.call_args[1]
        self.assertEqual(call_kwargs["keyword"], "python")


if __name__ == "__main__":
    unittest.main()
