"""IssueHunt API 客户端封装，支持搜索和认领赏金 issue"""

import os
import time
import logging
from typing import Any, Optional

import requests

logger = logging.getLogger(__name__)

ISSUEHUNT_API_BASE = "https://api.issuehunt.io"
ISSUEHUNT_WEB_BASE = "https://issuehunt.io"

DEFAULT_TIMEOUT = 30
DEFAULT_RATE_LIMIT_DELAY = 1.0
MAX_RETRIES = 3
RETRY_BACKOFF = 2


class IssueHuntAPIError(Exception):
    pass


class RateLimitError(IssueHuntAPIError):
    pass


class AuthenticationError(IssueHuntAPIError):
    pass


class IssueHuntClient:
    def __init__(
        self,
        token: Optional[str] = None,
        api_base: Optional[str] = None,
        timeout: int = DEFAULT_TIMEOUT,
        rate_limit_delay: float = DEFAULT_RATE_LIMIT_DELAY,
    ):
        self.token = token or os.environ.get("ISSUEHUNT_TOKEN", "")
        self.api_base = (api_base or ISSUEHUNT_API_BASE).rstrip("/")
        self.timeout = timeout
        self.rate_limit_delay = rate_limit_delay
        self._session = requests.Session()
        self._last_request_time = 0.0

        if self.token:
            self._session.headers.update({
                "Authorization": f"bearer {self.token}",
                "Content-Type": "application/json",
            })

    def _wait_for_rate_limit(self) -> None:
        elapsed = time.time() - self._last_request_time
        if elapsed < self.rate_limit_delay:
            time.sleep(self.rate_limit_delay - elapsed)
        self._last_request_time = time.time()

    def _request(
        self,
        method: str,
        endpoint: str,
        params: Optional[dict] = None,
        json_data: Optional[dict] = None,
    ) -> dict[str, Any]:
        url = f"{self.api_base}{endpoint}"
        self._wait_for_rate_limit()

        last_error: Optional[Exception] = None
        for attempt in range(1, MAX_RETRIES + 1):
            try:
                response = getattr(self._session, method.lower())(
                    url,
                    params=params,
                    json=json_data,
                    timeout=self.timeout,
                )
                if response.status_code == 429:
                    retry_after = int(response.headers.get("Retry-After", RETRY_BACKOFF * attempt))
                    logger.warning(f"触发限流，等待 {retry_after}s 后重试 ({attempt}/{MAX_RETRIES})")
                    time.sleep(retry_after)
                    continue
                if response.status_code == 401:
                    raise AuthenticationError("IssueHunt 认证失败，请检查 ISSUEHUNT_TOKEN")
                response.raise_for_status()
                return response.json()
            except requests.exceptions.Timeout as e:
                last_error = e
                logger.warning(f"请求超时 ({attempt}/{MAX_RETRIES}): {e}")
            except requests.exceptions.ConnectionError as e:
                last_error = e
                logger.warning(f"连接错误 ({attempt}/{MAX_RETRIES}): {e}")
            except requests.exceptions.HTTPError as e:
                last_error = e
                logger.warning(f"HTTP 错误 ({attempt}/{MAX_RETRIES}): {e}")

            if attempt < MAX_RETRIES:
                time.sleep(RETRY_BACKOFF ** attempt)

        raise IssueHuntAPIError(f"请求失败（已重试 {MAX_RETRIES} 次）: {last_error}")

    def search_issues(
        self,
        keyword: str = "",
        language: Optional[str] = None,
        min_amount: Optional[int] = None,
        max_amount: Optional[int] = None,
        sort: str = "updated",
        order: str = "desc",
        limit: int = 20,
        offset: int = 0,
    ) -> list[dict[str, Any]]:
        params: dict[str, Any] = {
            "limit": limit,
            "offset": offset,
            "sort": sort,
            "order": order,
        }
        if keyword:
            params["q"] = keyword
        if language:
            params["language"] = language
        if min_amount is not None:
            params["minAmount"] = min_amount
        if max_amount is not None:
            params["maxAmount"] = max_amount

        data = self._request("GET", "/v1/issues", params=params)
        issues = data.get("issues", [])
        result = []
        for issue in issues:
            result.append(self._normalize_issue(issue))
        return result

    def get_issue_details(
        self,
        owner: str,
        repo: str,
        issue_number: int,
    ) -> dict[str, Any]:
        data = self._request(
            "GET",
            f"/v1/repos/{owner}/{repo}/issues/{issue_number}",
        )
        return self._normalize_issue(data)

    def get_issue_bounty_info(
        self,
        owner: str,
        repo: str,
        issue_number: int,
    ) -> dict[str, Any]:
        data = self.get_issue_details(owner, repo, issue_number)
        return {
            "bounty_amount": data.get("bounty_amount", 0),
            "currency": data.get("currency", "USD"),
            "status": data.get("bounty_status", "unknown"),
            "claimants": data.get("claimants", []),
            "is_claimed_by_me": data.get("is_claimed_by_me", False),
            "funded": data.get("funded", False),
        }

    def claim_issue(
        self,
        owner: str,
        repo: str,
        issue_number: int,
    ) -> dict[str, Any]:
        if not self.token:
            raise AuthenticationError("需要 ISSUEHUNT_TOKEN 才能认领 issue")

        data = self._request(
            "POST",
            f"/v1/repos/{owner}/{repo}/issues/{issue_number}/claim",
        )
        return {
            "success": True,
            "message": data.get("message", "认领成功"),
            "claim_id": data.get("id"),
        }

    def get_my_claims(self, limit: int = 20) -> list[dict[str, Any]]:
        if not self.token:
            raise AuthenticationError("需要 ISSUEHUNT_TOKEN 才能查看认领记录")

        data = self._request(
            "GET",
            "/v1/me/claims",
            params={"limit": limit},
        )
        claims = data.get("claims", [])
        return [
            {
                "id": c.get("id"),
                "owner": c.get("repository", {}).get("owner", {}).get("login", ""),
                "repo": c.get("repository", {}).get("name", ""),
                "issue_number": c.get("issueNumber"),
                "title": c.get("title", ""),
                "status": c.get("status", "unknown"),
                "bounty_amount": c.get("bountyAmount", 0),
                "created_at": c.get("createdAt", ""),
            }
            for c in claims
        ]

    def _normalize_issue(self, raw: dict[str, Any]) -> dict[str, Any]:
        bounty = raw.get("bountyAmount") or raw.get("bounty_amount") or 0
        repository = raw.get("repository") or {}
        repo_name = (
            repository.get("nameWithOwner")
            or repository.get("fullName")
            or f"{repository.get('owner', {}).get('login', '')}/{repository.get('name', '')}"
        )
        if repo_name == "/":
            repo_name = ""

        labels_raw = raw.get("labels") or []
        labels = []
        for lbl in labels_raw:
            if isinstance(lbl, dict):
                labels.append(lbl.get("name", ""))
            elif isinstance(lbl, str):
                labels.append(lbl)

        claimants_raw = raw.get("claimants") or raw.get("claims") or []
        claimants = []
        for c in claimants_raw:
            if isinstance(c, dict):
                user = c.get("user") or c
                claimants.append(user.get("login", user.get("name", "")))
            elif isinstance(c, str):
                claimants.append(c)

        return {
            "id": raw.get("id"),
            "number": raw.get("number") or raw.get("issueNumber"),
            "title": raw.get("title", ""),
            "body": raw.get("body", "") or raw.get("description", ""),
            "url": raw.get("url") or raw.get("htmlUrl", ""),
            "repository": repo_name,
            "owner": repository.get("owner", {}).get("login", "")
            if isinstance(repository.get("owner"), dict)
            else "",
            "repo": repository.get("name", ""),
            "labels": labels,
            "created_at": raw.get("createdAt") or raw.get("created_at", ""),
            "updated_at": raw.get("updatedAt") or raw.get("updated_at", ""),
            "state": raw.get("state", "open"),
            "bounty_amount": bounty,
            "currency": raw.get("currency", "USD"),
            "bounty_status": raw.get("bountyStatus") or raw.get("bounty_status", "open"),
            "claimants": claimants,
            "is_claimed_by_me": raw.get("claimedByMe", False)
            or raw.get("is_claimed_by_me", False),
            "funded": raw.get("funded", bounty > 0),
            "pr_count": raw.get("pullRequestCount") or raw.get("prCount", 0),
            "comment_count": raw.get("commentCount") or raw.get("comments", 0),
        }

    def health_check(self) -> bool:
        try:
            self._request("GET", "/v1/health")
            return True
        except Exception as e:
            logger.warning(f"健康检查失败: {e}")
            return False


def create_client(token: Optional[str] = None) -> IssueHuntClient:
    return IssueHuntClient(token=token)
