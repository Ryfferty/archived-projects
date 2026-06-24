"""多平台聚合器，统一搜索和管理多个赏金平台"""

import json
import logging
import os
import subprocess
import time
from abc import ABC, abstractmethod
from dataclasses import dataclass, field
from typing import Any, Optional

import requests

logger = logging.getLogger(__name__)


@dataclass
class SearchResult:
    platform: str
    title: str
    url: str
    repository: str
    bounty_amount: int = 0
    currency: str = "USD"
    labels: list[str] = field(default_factory=list)
    created_at: str = ""
    state: str = "open"
    raw_data: dict[str, Any] = field(default_factory=dict)

    def to_dict(self) -> dict[str, Any]:
        return {
            "platform": self.platform,
            "title": self.title,
            "url": self.url,
            "repository": self.repository,
            "bounty_amount": self.bounty_amount,
            "currency": self.currency,
            "labels": self.labels,
            "created_at": self.created_at,
            "state": self.state,
        }


class PlatformAdapter(ABC):
    @abstractmethod
    def name(self) -> str: ...

    @abstractmethod
    def search(
        self,
        keyword: str = "",
        language: Optional[str] = None,
        min_amount: Optional[int] = None,
        limit: int = 20,
    ) -> list[SearchResult]: ...

    @abstractmethod
    def is_available(self) -> bool: ...


class GitHubPlatformAdapter(PlatformAdapter):
    def __init__(self):
        self._has_gh = False
        try:
            result = subprocess.run(
                ["gh", "auth", "status"],
                capture_output=True,
                timeout=10,
            )
            self._has_gh = result.returncode == 0
        except Exception:
            self._has_gh = False

    def name(self) -> str:
        return "github"

    def is_available(self) -> bool:
        return self._has_gh

    def search(
        self,
        keyword: str = "",
        language: Optional[str] = None,
        min_amount: Optional[int] = None,
        limit: int = 20,
    ) -> list[SearchResult]:
        labels = ["bounty", "💰 Bounty", "💎 Bounty", "赏金"]
        query_parts = [f'label:"{l}"' for l in labels]
        query_parts.append("is:open")

        if keyword:
            query_parts.append(keyword)

        if language:
            query_parts.append(f"language:{language}")

        if min_amount and min_amount > 0:
            query_parts.append(f'"${min_amount}" bounty')

        query = " ".join(query_parts)

        try:
            result = subprocess.run(
                [
                    "gh", "search", "issues", query,
                    "--limit", str(limit),
                    "--json", "repository,title,url,labels,createdAt,state,number",
                    "--sort", "updated",
                    "--order", "desc",
                ],
                capture_output=True,
                text=True,
                timeout=120,
            )

            if result.returncode != 0:
                logger.warning(f"GitHub 搜索失败: {result.stderr}")
                return []

            issues = json.loads(result.stdout.strip())
            results = []
            for issue in issues:
                labels_list = [lbl.get("name", "") for lbl in (issue.get("labels") or []) if isinstance(lbl, dict)]
                results.append(SearchResult(
                    platform="github",
                    title=issue.get("title", ""),
                    url=issue.get("url", ""),
                    repository=(issue.get("repository") or {}).get("nameWithOwner", ""),
                    labels=labels_list,
                    created_at=issue.get("createdAt", ""),
                    state=issue.get("state", "open"),
                    raw_data=issue,
                ))
            return results
        except subprocess.TimeoutExpired:
            logger.error("GitHub 搜索超时")
            return []
        except (json.JSONDecodeError, FileNotFoundError) as e:
            logger.error(f"GitHub 搜索错误: {e}")
            return []


class IssueHuntPlatformAdapter(PlatformAdapter):
    def __init__(self, token: Optional[str] = None):
        self._token = token or os.environ.get("ISSUEHUNT_TOKEN", "")
        self._client = None

    def _get_client(self):
        if self._client is None:
            from scripts.issuehunt_api import IssueHuntClient
            self._client = IssueHuntClient(token=self._token)
        return self._client

    def name(self) -> str:
        return "issuehunt"

    def is_available(self) -> bool:
        if not self._token:
            return False
        try:
            client = self._get_client()
            return client.health_check()
        except Exception:
            return False

    def search(
        self,
        keyword: str = "",
        language: Optional[str] = None,
        min_amount: Optional[int] = None,
        limit: int = 20,
    ) -> list[SearchResult]:
        try:
            client = self._get_client()
            issues = client.search_issues(
                keyword=keyword,
                language=language,
                min_amount=min_amount,
                limit=limit,
            )
            results = []
            for issue in issues:
                results.append(SearchResult(
                    platform="issuehunt",
                    title=issue.get("title", ""),
                    url=issue.get("url", ""),
                    repository=issue.get("repository", ""),
                    bounty_amount=issue.get("bounty_amount", 0),
                    currency=issue.get("currency", "USD"),
                    labels=issue.get("labels", []),
                    created_at=issue.get("created_at", ""),
                    state=issue.get("state", "open"),
                    raw_data=issue,
                ))
            return results
        except Exception as e:
            logger.error(f"IssueHunt 搜索错误: {e}")
            return []


class AlgoraPlatformAdapter(PlatformAdapter):
    def __init__(self, token: Optional[str] = None):
        self._token = token or os.environ.get("ALGORA_TOKEN", "")

    def name(self) -> str:
        return "algora"

    def is_available(self) -> bool:
        return bool(self._token)

    def search(
        self,
        keyword: str = "",
        language: Optional[str] = None,
        min_amount: Optional[int] = None,
        limit: int = 20,
    ) -> list[SearchResult]:
        headers = {}
        if self._token:
            headers["Authorization"] = f"Bearer {self._token}"

        params: dict[str, Any] = {"limit": limit}
        if keyword:
            params["q"] = keyword
        if min_amount:
            params["minAmount"] = min_amount

        try:
            resp = requests.get(
                "https://api.algora.io/v0/bounties",
                headers=headers,
                params=params,
                timeout=30,
            )
            resp.raise_for_status()
            data = resp.json()

            results = []
            items = data.get("bounties") or data if isinstance(data, list) else []
            for item in items[:limit]:
                results.append(SearchResult(
                    platform="algora",
                    title=item.get("title", ""),
                    url=item.get("url", f"https://algora.io/{item.get('id', '')}"),
                    repository=item.get("repository", item.get("fullName", "")),
                    bounty_amount=int(item.get("amount", item.get("bountyAmount", 0))),
                    currency=item.get("currency", "USD"),
                    labels=item.get("tags", []),
                    created_at=item.get("createdAt", ""),
                    state=item.get("status", "open"),
                    raw_data=item,
                ))
            return results
        except Exception as e:
            logger.error(f"Algora 搜索错误: {e}")
            return []


@dataclass
class AggregatedResult:
    total_results: int = 0
    by_platform: dict[str, int] = field(default_factory=dict)
    results: list[SearchResult] = field(default_factory=list)
    errors: list[str] = field(default_factory=list)
    duration_seconds: float = 0.0

    def to_dict(self) -> dict[str, Any]:
        return {
            "total_results": self.total_results,
            "by_platform": self.by_platform,
            "results": [r.to_dict() for r in self.results],
            "errors": self.errors,
            "duration_seconds": round(self.duration_seconds, 2),
        }


class PlatformAggregator:
    def __init__(self):
        self._adapters: list[PlatformAdapter] = []

    def register(self, adapter: PlatformAdapter) -> "PlatformAggregator":
        self._adapters.append(adapter)
        return self

    def register_all(self) -> "PlatformAggregator":
        self.register(GitHubPlatformAdapter())
        self.register(IssueHuntPlatformAdapter())
        algora_token = os.environ.get("ALGORA_TOKEN", "")
        if algora_token:
            self.register(AlgoraPlatformAdapter(token=algora_token))
        return self

    def search_all(
        self,
        keyword: str = "",
        language: Optional[str] = None,
        min_amount: Optional[int] = None,
        limit_per_platform: int = 20,
        deduplicate: bool = True,
    ) -> AggregatedResult:
        start_time = time.time()
        result = AggregatedResult()

        seen_urls: set[str] = set()

        for adapter in self._adapters:
            platform_name = adapter.name()

            if not adapter.is_available():
                logger.info(f"平台 '{platform_name}' 不可用，跳过")
                continue

            try:
                logger.info(f"搜索平台: {platform_name}")
                items = adapter.search(
                    keyword=keyword,
                    language=language,
                    min_amount=min_amount,
                    limit=limit_per_platform,
                )

                added = 0
                for item in items:
                    if deduplicate and item.url in seen_urls:
                        continue
                    if deduplicate:
                        seen_urls.add(item.url)
                    result.results.append(item)
                    added += 1

                result.by_platform[platform_name] = added
                logger.info(f"{platform_name}: 找到 {added} 个结果")
            except Exception as e:
                error_msg = f"{platform_name} 搜索异常: {e}"
                logger.error(error_msg)
                result.errors.append(error_msg)

        result.total_results = len(result.results)
        result.duration_seconds = time.time() - start_time

        result.results.sort(key=lambda x: x.bounty_amount, reverse=True)

        return result


def create_aggregator() -> PlatformAggregator:
    return PlatformAggregator().register_all()


def quick_search(keyword: str = "", language: Optional[str] = None, min_amount: int = 0) -> AggregatedResult:
    agg = create_aggregator()
    return agg.search_all(
        keyword=keyword,
        language=language,
        min_amount=min_amount if min_amount > 0 else None,
    )
