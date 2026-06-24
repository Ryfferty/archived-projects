"""IssueHunt API 客户端单元测试"""

import json
import os
import sys
import unittest
from unittest.mock import MagicMock, patch, PropertyMock

sys.path.insert(0, os.path.join(os.path.dirname(__file__), ".."))

from scripts.issuehunt_api import (
    AuthenticationError,
    IssueHuntAPIError,
    IssueHuntClient,
    RateLimitError,
    create_client,
)


class TestIssueHuntClientInit(unittest.TestCase):
    def test_init_with_token(self):
        client = IssueHuntClient(token="test_token_123")
        self.assertEqual(client.token, "test_token_123")
        self.assertIn("Authorization", client._session.headers)

    def test_init_with_env_token(self):
        with patch.dict(os.environ, {"ISSUEHUNT_TOKEN": "env_token_456"}):
            client = IssueHuntClient()
            self.assertEqual(client.token, "env_token_456")

    def test_init_no_token(self):
        with patch.dict(os.environ, {}, clear=True):
            client = IssueHuntClient()
            self.assertEqual(client.token, "")

    def test_custom_api_base(self):
        client = IssueHuntClient(api_base="https://custom.api.com")
        self.assertEqual(client.api_base, "https://custom.api.com")

    def test_default_values(self):
        client = IssueHuntClient()
        self.assertEqual(client.timeout, 30)
        self.assertEqual(client.rate_limit_delay, 1.0)


class TestNormalizeIssue(unittest.TestCase):
    def setUp(self):
        self.client = IssueHuntClient(token="test")

    def test_full_issue_data(self):
        raw = {
            "id": 12345,
            "number": 42,
            "title": "Fix authentication bug",
            "body": "The login fails when using OAuth",
            "url": "https://issuehunt.io/repos/owner/repo/issues/42",
            "repository": {
                "nameWithOwner": "owner/repo",
                "name": "repo",
                "owner": {"login": "owner"},
            },
            "labels": [{"name": "bounty"}, {"name": "bug"}],
            "createdAt": "2024-01-15T10:30:00Z",
            "updatedAt": "2024-01-20T14:00:00Z",
            "state": "open",
            "bountyAmount": 100,
            "currency": "USD",
            "bountyStatus": "open",
            "claimants": [{"user": {"login": "dev1"}}, {"user": {"login": "dev2"}}],
            "claimedByMe": False,
            "funded": True,
            "pullRequestCount": 1,
            "commentCount": 5,
        }
        result = self.client._normalize_issue(raw)
        self.assertEqual(result["id"], 12345)
        self.assertEqual(result["number"], 42)
        self.assertEqual(result["title"], "Fix authentication bug")
        self.assertEqual(result["repository"], "owner/repo")
        self.assertEqual(result["labels"], ["bounty", "bug"])
        self.assertEqual(result["bounty_amount"], 100)
        self.assertEqual(result["currency"], "USD")
        self.assertEqual(len(result["claimants"]), 2)
        self.assertTrue(result["funded"])

    def test_minimal_issue_data(self):
        raw = {
            "id": 1,
            "title": "Minimal issue",
            "repository": {},
        }
        result = self.client._normalize_issue(raw)
        self.assertEqual(result["title"], "Minimal issue")
        self.assertEqual(result["repository"], "")
        self.assertEqual(result["bounty_amount"], 0)
        self.assertEqual(result["labels"], [])

    def test_string_labels(self):
        raw = {
            "id": 1,
            "title": "Test",
            "repository": {},
            "labels": ["bounty", "bug"],
        }
        result = self.client._normalize_issue(raw)
        self.assertEqual(result["labels"], ["bounty", "bug"])

    def test_string_claimants(self):
        raw = {
            "id": 1,
            "title": "Test",
            "repository": {},
            "claims": ["user1", "user2"],
        }
        result = self.client._normalize_issue(raw)
        self.assertEqual(result["claimants"], ["user1", "user2"])


class TestSearchIssues(unittest.TestCase):
    def setUp(self):
        self.client = IssueHuntClient(token="test_token")

    @patch("scripts.issuehunt_api.IssueHuntClient._request")
    def test_search_basic(self, mock_request):
        mock_request.return_value = {
            "issues": [
                {
                    "id": 1,
                    "title": "Bounty issue 1",
                    "repository": {"nameWithOwner": "org/repo1"},
                    "bountyAmount": 50,
                },
                {
                    "id": 2,
                    "title": "Bounty issue 2",
                    "repository": {"nameWithOwner": "org/repo2"},
                    "bountyAmount": 100,
                },
            ]
        }

        results = self.client.search_issues(keyword="authentication")

        self.assertEqual(len(results), 2)
        self.assertEqual(results[0]["title"], "Bounty issue 1")
        self.assertEqual(results[1]["bounty_amount"], 100)
        mock_request.assert_called_once()
        call_args = mock_request.call_args
        self.assertEqual(call_args[0][0], "GET")
        self.assertIn("/v1/issues", call_args[0][1])

    @patch("scripts.issuehunt_api.IssueHuntClient._request")
    def test_search_with_filters(self, mock_request):
        mock_request.return_value = {"issues": []}

        self.client.search_issues(
            keyword="python bug",
            language="python",
            min_amount=50,
            max_amount=200,
            limit=10,
        )

        params = mock_request.call_args[1]["params"]
        self.assertEqual(params["q"], "python bug")
        self.assertEqual(params["language"], "python")
        self.assertEqual(params["minAmount"], 50)
        self.assertEqual(params["maxAmount"], 200)
        self.assertEqual(params["limit"], 10)

    @patch("scripts.issuehunt_api.IssueHuntClient._request")
    def test_search_empty_result(self, mock_request):
        mock_request.return_value = {"issues": []}
        results = self.client.search_issues()
        self.assertEqual(results, [])


class TestGetIssueDetails(unittest.TestCase):
    def setUp(self):
        self.client = IssueHuntClient(token="test_token")

    @patch("scripts.issuehunt_api.IssueHuntClient._request")
    def test_get_details_success(self, mock_request):
        mock_request.return_value = {
            "id": 999,
            "number": 10,
            "title": "Important bounty issue",
            "repository": {"nameWithOwner": "cool/project"},
            "bountyAmount": 150,
            "state": "open",
        }

        result = self.client.get_issue_details("cool", "project", 10)

        self.assertEqual(result["number"], 10)
        self.assertEqual(result["bounty_amount"], 150)
        mock_request.assert_called_once_with(
            "GET", "/v1/repos/cool/project/issues/10"
        )


class TestBountyInfo(unittest.TestCase):
    def setUp(self):
        self.client = IssueHuntClient(token="test_token")

    @patch("scripts.issuehunt_api.IssueHuntClient.get_issue_details")
    def test_bounty_info_extraction(self, mock_details):
        mock_details.return_value = {
            "bounty_amount": 75,
            "currency": "USD",
            "bounty_status": "open",
            "claimants": ["alice"],
            "is_claimed_by_me": False,
            "funded": True,
        }

        info = self.client.get_issue_bounty_info("owner", "repo", 5)

        self.assertEqual(info["bounty_amount"], 75)
        self.assertEqual(info["status"], "open")
        self.assertFalse(info["is_claimed_by_me"])
        self.assertTrue(info["funded"])


class TestClaimIssue(unittest.TestCase):
    def test_claim_without_token(self):
        client = IssueHuntClient(token="")
        with self.assertRaises(AuthenticationError):
            client.claim_issue("owner", "repo", 1)

    @patch("scripts.issuehunt_api.IssueHuntClient._request")
    def test_claim_success(self, mock_request):
        client = IssueHuntClient(token="valid_token")
        mock_request.return_value = {"id": "claim-001", "message": "Claimed successfully"}

        result = client.claim_issue("owner", "repo", 42)

        self.assertTrue(result["success"])
        self.assertEqual(result["claim_id"], "claim-001")


class TestMyClaims(unittest.TestCase):
    def test_my_claims_without_token(self):
        client = IssueHuntClient(token="")
        with self.assertRaises(AuthenticationError):
            client.get_my_claims()

    @patch("scripts.issuehunt_api.IssueHuntClient._request")
    def test_get_my_claims_success(self, mock_request):
        client = IssueHuntClient(token="token")
        mock_request.return_value = {
            "claims": [
                {
                    "id": "c1",
                    "repository": {"owner": {"login": "org"}, "name": "repo"},
                    "issueNumber": 10,
                    "title": "Fix bug",
                    "status": "in_progress",
                    "bountyAmount": 50,
                    "createdAt": "2024-01-01T00:00:00Z",
                }
            ]
        }

        claims = client.get_my_claims()

        self.assertEqual(len(claims), 1)
        self.assertEqual(claims[0]["issue_number"], 10)
        self.assertEqual(claims[0]["bounty_amount"], 50)


class TestErrorHandling(unittest.TestCase):
    @patch("scripts.issuehunt_api.requests.Session.get")
    def test_auth_error_401(self, mock_get):
        response = MagicMock()
        response.status_code = 401
        response.raise_for_status.side_effect = Exception("401")
        mock_get.return_value = response

        client = IssueHuntClient(token="bad_token")
        with self.assertRaises(AuthenticationError):
            client._request("GET", "/test")

    @patch("scripts.issuehunt_api.time.sleep")
    @patch("scripts.issuehunt_api.requests.Session.get")
    def test_rate_limit_retry(self, mock_get, mock_sleep):
        rate_limited_response = MagicMock()
        rate_limited_response.status_code = 429
        rate_limited_response.headers = {"Retry-After": "2"}

        ok_response = MagicMock()
        ok_response.status_code = 200
        ok_response.json.return_value = {"data": "ok"}
        ok_response.raise_for_status.return_value = None

        mock_get.side_effect = [
            (rate_limited_response, None)[0],
            Exception("raise"),
            ok_response,
        ]

        original_get = None
        call_count = [0]

        def get_side_effect(*args, **kwargs):
            call_count[0] += 1
            if call_count[0] == 1:
                resp = MagicMock()
                resp.status_code = 429
                resp.headers = {"Retry-After": "1"}
                resp.raise_for_status.side_effect = Exception("429")
                return resp
            elif call_count[0] == 2:
                resp = MagicMock()
                resp.status_code = 200
                resp.json.return_value = {"data": "ok"}
                resp.raise_for_status.return_value = None
                return resp
            return MagicMock()

        mock_get.side_effect = get_side_effect

        client = IssueHuntClient(token="test")
        result = client._request("GET", "/test")
        self.assertEqual(result, {"data": "ok"})

    @patch("scripts.issuehunt_api.requests.Session.get")
    def test_max_retries_exhausted(self, mock_get):
        import requests.exceptions
        mock_get.side_effect = requests.exceptions.ConnectionError("Connection refused")

        client = IssueHuntClient(token="test")
        with self.assertRaises(IssueHuntAPIError):
            client._request("GET", "/fail")


class TestCreateClient(unittest.TestCase):
    def test_create_client_factory(self):
        client = create_client(token="factory_token")
        self.assertIsInstance(client, IssueHuntClient)
        self.assertEqual(client.token, "factory_token")

    def test_create_client_no_args(self):
        client = create_client()
        self.assertIsInstance(client, IssueHuntClient)


class TestHealthCheck(unittest.TestCase):
    @patch("scripts.issuehunt_api.IssueHuntClient._request")
    def test_health_check_success(self, mock_request):
        client = IssueHuntClient(token="test")
        self.assertTrue(client.health_check())
        mock_request.assert_called_once_with("GET", "/v1/health")

    @patch("scripts.issuehunt_api.IssueHuntClient._request")
    def test_health_check_failure(self, mock_request):
        client = IssueHuntClient(token="test")
        mock_request.side_effect = Exception("Service unavailable")
        self.assertFalse(client.health_check())


if __name__ == "__main__":
    unittest.main()
