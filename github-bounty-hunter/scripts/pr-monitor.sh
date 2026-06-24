#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

DEFAULT_STATE="all"
DEFAULT_REPO=""
DEFAULT_AUTHOR=""
DEFAULT_LIMIT=20
MAX_RETRIES=3
RETRY_DELAY=5

usage() {
    cat <<EOF
GitHub Bounty Hunter - PR 状态监控脚本

用法: $(basename "$0") [选项]

选项:
    -s, --state STATE       PR 筛选状态: open, closed, merged, all (默认: all)
    -r, --repo OWNER/REPO   指定仓库 (如: owner/repo)
    -a, --author AUTHOR      按作者筛选
    -n, --limit NUM          返回数量上限 (默认: $DEFAULT_LIMIT)
    -p, --proxy URL          代理地址 (如: http://127.0.0.1:7890 或 ghfast.top)
    -o, --output FILE        输出文件路径 (默认: stdout)
    -j, --json               强制 JSON 输出
    -w, --watch              持续监控模式 (每 60s 刷新一次)
    --cron                   Cron 模式，适合定时任务（静默输出）
    -h, --help               显示帮助信息

示例:
    $(basename "$0") -s open -a myusername
    $(basename "$0") -r owner/repo -o pr-status.json
    $(basename "$0") --watch -p http://ghfast.top

输出字段:
    number, title, state, url, repository,
    createdAt, updatedAt, mergedAt, closedAt,
    ciStatus, reviewStatus, additions, deletions,
    changedFiles, labels, headRefName, baseRefName
EOF
}

log_info() {
    echo "[INFO] $(date '+%Y-%m-%d %H:%M:%S') - $*" >&2
}

log_error() {
    echo "[ERROR] $(date '+%Y-%m-%d %H:%M:%S') - $*" >&2
}

log_warn() {
    echo "[WARN] $(date '+%Y-%m-%d %H:%M:%S') - $*" >&2
}

check_dependencies() {
    local missing=()

    if ! command -v gh &>/dev/null; then
        missing+=("gh")
    fi

    if ! command -v jq &>/dev/null; then
        missing+=("jq")
    fi

    if [[ ${#missing[@]} -gt 0 ]]; then
        log_error "缺少必要依赖: ${missing[*]}"
        exit 1
    fi

    if ! gh auth status &>/dev/null; then
        log_error "GitHub CLI 未认证。请运行 'gh auth login' 进行认证"
        exit 1
    fi
}

setup_proxy() {
    local proxy_url="$1"
    [[ -z "$proxy_url" ]] && return

    if [[ "$proxy_url" == "ghfast.top" || "$proxy_url" == *"ghfast"* ]]; then
        export HTTP_PROXY="http://ghfast.top"
        export HTTPS_PROXY="http://ghfast.top"
        export ALL_PROXY="http://ghfast.top"
        export GH_HOST="github.com"
        log_info "已设置 ghfast.top 代理"
    else
        export HTTP_PROXY="$proxy_url"
        export HTTPS_PROXY="$proxy_url"
        export ALL_PROXY="$proxy_url"
        log_info "已设置代理: $proxy_url"
    fi
}

get_pr_list_with_retry() {
    local repo="$1"
    local state="$2"
    local author="$3"
    local limit="$4"
    local attempt=1
    local result=""
    local args=(
        --limit "$limit"
        --json "number,title,state,url,repository,createdAt,updatedAt,"
               "mergedAt,closedAt,headRefName,baseRefName,"
               "additions,deletions,changedFiles,labels,reviewDecision,statusCheckRollup"
        --sort updated
        --order desc
    )

    [[ -n "$repo" ]] && args+=("--repo" "$repo")
    [[ -n "$author" ]] && args+=("--author" "$author")
    [[ "$state" != "all" ]] && args+=("--state" "$state")

    while [[ $attempt -le $MAX_RETRIES ]]; do
        log_info "获取 PR 列表 (尝试 $attempt/$MAX_RETRIES)..."

        result=$(gh pr list "${args[@]}" 2>&1) && break

        log_warn "获取失败 (尝试 $attempt/$MAX_RETRIES): $result"

        if [[ $attempt -lt $MAX_RETRIES ]]; then
            log_info "等待 ${RETRY_DELAY}s 后重试..."
            sleep $RETRY_DELAY
        fi
        ((attempt++))
    done

    if [[ $attempt -gt $MAX_RETRIES ]]; then
        log_error "获取 PR 列表失败，已达最大重试次数"
        return 1
    fi

    echo "$result"
}

extract_ci_status() {
    local pr_json="$1"

    local rollup
    rollup=$(echo "$pr_json" | jq -r '.statusCheckRollup // empty')

    if [[ -z "$rollup" || "$rollup" == "null" ]]; then
        echo "unknown"
        return
    fi

    local count total pending failure success skipped
    count=$(echo "$rollup" | jq 'length')
    total=${count:-0}
    pending=$(echo "$rollup" | jq '[.[] | select(.status=="PENDING")] | length')
    failure=$(echo "$rollup" | jq '[.[] | select(.status=="FAILURE") or (.status=="ERROR")] | length')
    success=$(echo "$rollup" | jq '[.[] | select(.status=="SUCCESS")] | length')
    skipped=$(echo "$rollup" | jq '[.[] | select(.status=="SKIPPED")] | length')

    if ((failure > 0)); then
        echo "failure"
    elif ((pending > 0)) && ((success >= 0)); then
        echo "pending"
    elif ((success > 0)) && ((pending == 0)) && ((failure == 0)); then
        echo "success"
    elif ((skipped == total)) && ((total > 0)); then
        echo "skipped"
    else
        echo "unknown"
    fi
}

extract_review_status() {
    local pr_json="$1"

    local decision
    decision=$(echo "$pr_json" | jq -r '.reviewDecision // "UNKNOWN"')

    case "$decision" in
        APPROVED)     echo "approved" ;;
        CHANGES_REQUESTED) echo "changes_requested" ;;
        REVIEW_REQUIRED)    echo "review_required" ;;
        *)            echo "unknown" ;;
    esac
}

process_pr_data() {
    local raw_json="$1"

    echo "$raw_json" | jq '
        map({
            number: .number,
            title: .title,
            state: .state,
            url: .url,
            repository: .repository.nameWithOwner,
            createdAt: .createdAt,
            updatedAt: .updatedAt,
            mergedAt: (.mergedAt // ""),
            closedAt: (.closedAt // ""),
            headRefName: .headRefName,
            baseRefName: .baseRefName,
            additions: .additions,
            deletions: .deletions,
            changedFiles: .changedFiles,
            labels: [.labels[].name],
            reviewDecision: (.reviewDecision // "UNKNOWN"),
            hasStatusChecks: ((.statusCheckRollup // []) | length > 0)
        })
    '

    echo "$raw_json" | jq -c '.[]' | while IFS= read -r line; do
        ci=$(extract_ci_status "$line")
        review=$(extract_review_status "$line")
        pr_num=$(echo "$line" | jq -r '.number')

        jq --arg num "$pr_num" \
           --arg ci "$ci" \
           --arg review "$review" \
           '(map(select(.number == ($num | tonumber))) | .[]) |
            .ciStatus = $ci |
            .reviewStatus = $review'
    done
}

process_prs_full() {
    local raw_json="$1"

    local processed
    processed=$(echo "$raw_json" | jq '
        def get_ci(s):
            if (s == null or (s | length) == 0) then "unknown"
            elif ([s[] | select(.status == "FAILURE" or .status == "ERROR")] | length > 0) then "failure"
            elif ([s[] | select(.status == "PENDING")] | length > 0) then "pending"
            elif ([s[] | select(.status == "SUCCESS")] | length > 0 and
                  ([s[] | select(.status == "PENDING")] | length) == 0 and
                  ([s[] | select(.status == "FAILURE" or .status == "ERROR")] | length) == 0) then "success"
            else "unknown"
            end;

        def get_review(d):
            if d == "APPROVED" then "approved"
            elif d == "CHANGES_REQUESTED" then "changes_requested"
            elif d == "REVIEW_REQUIRED" then "review_required"
            else "unknown"
            end;

        map({
            number: .number,
            title: .title,
            state: .state,
            url: .url,
            repository: .repository.nameWithOwner,
            createdAt: .createdAt,
            updatedAt: .updatedAt,
            mergedAt: (.mergedAt // ""),
            closedAt: (.closedAt // ""),
            headRefName: .headRefName,
            baseRefName: .baseRefName,
            additions: .additions,
            deletions: .deletions,
            changedFiles: .changedFiles,
            labels: [.labels[].name],
            ciStatus: get_ci(.statusCheckRollup),
            reviewStatus: get_review(.reviewDecision),
            reviewDecisionRaw: (.reviewDecision // "UNKNOWN"),
            hasStatusChecks: ((.statusCheckRollup // []) | length > 0)
        }) | sort_by(.updatedAt) | reverse
    ')

    echo "$processed"
}

generate_summary() {
    local json_data="$1"

    local total open_count closed_count merged_count
    total=$(echo "$json_data" | jq 'length')
    open_count=$(echo "$json_data" | jq '[.[] | select(.state == "OPEN")] | length')
    closed_count=$(echo "$json_data" | jq '[.[] | select(.state == "CLOSED")] | length')
    merged_count=$(echo "$json_data" | jq '[.[] | select(.state == "MERGED")] | length')

    local ci_success ci_pending ci_failure ci_unknown
    ci_success=$(echo "$json_data" | jq '[.[] | select(.ciStatus == "success")] | length')
    ci_pending=$(echo "$json_data" | jq '[.[] | select(.ciStatus == "pending")] | length')
    ci_failure=$(echo "$json_data" | jq '[.[] | select(.ciStatus == "failure")] | length')
    ci_unknown=$(echo "$json_data" | jq '[.[] | select(.ciStatus == "unknown")] | length')

    local review_approved review_changes review_required review_unknown
    review_approved=$(echo "$json_data" | jq '[.[] | select(.reviewStatus == "approved")] | length')
    review_changes=$(echo "$json_data" | jq '[.[] | select(.reviewStatus == "changes_requested")] | length')
    review_required=$(echo "$json_data" | jq '[.[] | select(.reviewStatus == "review_required")] | length')
    review_unknown=$(echo "$json_data" | jq '[.[] | select(.reviewStatus == "unknown")] | length')

    jq -n \
        --argjson total "$total" \
        --argjson open "$open_count" \
        --argjson closed "$closed_count" \
        --argjson merged "$merged_count" \
        --argjson ci_success "$ci_success" \
        --argjson ci_pending "$ci_pending" \
        --argjson ci_failure "$ci_failure" \
        --argjson ci_unknown "$ci_unknown" \
        --argjson review_approved "$review_approved" \
        --argjson review_changes "$review_changes" \
        --argjson review_required "$review_required" \
        --argjson review_unknown "$review_unknown" \
        '{
            totalPRs: $total,
            byState: {open: $open, closed: $closed, merged: $merged},
            ciStatus: {success: $ci_success, pending: $ci_pending, failure: $ci_failure, unknown: $ci_unknown},
            reviewStatus: {approved: $review_approved, changesRequested: $review_changes, required: $review_required, unknown: $review_unknown}
        }'
}

format_report() {
    local pr_json="$1"
    local summary_json="$2"
    local quiet="${3:-false}"

    if [[ "$quiet" == "true" ]]; then
        return
    fi

    local total open_cnt closed_cnt merged_cnt
    total=$(echo "$summary_json" | jq '.totalPRs')
    open_cnt=$(echo "$summary_json" | jq '.byState.open')
    closed_cnt=$(echo "$summary_json" | jq '.byState.closed')
    merged_cnt=$(echo "$summary_json" | jq '.byState.merged')
    local ci_fail=$(echo "$summary_json" | jq '.ciStatus.failure')
    local ci_pend=$(echo "$summary_json" | jq '.ciStatus.pending')
    local rev_app=$(echo "$summary_json" | jq '.reviewStatus.approved')
    local rev_chg=$(echo "$summary_json" | jq '.reviewStatus.changesRequested')

    cat <<EOF

╔══════════════════════════════════════════════╗
║     GitHub Bounty Hunter - PR 状态报告       ║
║     $(date '+%Y-%m-%d %H:%M:%S')              ║
╠══════════════════════════════════════════════╣
║  总计: ${total}  │  🟢 开启: ${open_cnt}  │  🔴 关闭: ${closed_cnt}  │  ✅ 合并: ${merged_cnt}  ║
╠══════════════════════════════════════════════╣
║  CI 状态: ✅通过 ── 失败:${ci_fail}  等待:${ci_pend}         ║
║  Review: 👍已批准:${rev_app}  🔁需修改:${rev_chg}             ║
╚══════════════════════════════════════════════╝
EOF

    echo "$pr_json" | jq -r '.[] | "  [\(.state)] #\(.number) \(.title) | CI:\(.ciStatus) Review:\(.reviewStatus) [+\.additions/-\(.deletions)]"' 2>/dev/null || true
}

watch_mode() {
    local repo="$1"
    local state="$2"
    local author="$3"
    local limit="$4"
    local interval=60

    log_info "进入持续监控模式 (每 ${interval}s 刷新)，按 Ctrl+C 停止..."

    while true; do
        clear 2>/dev/null || true

        local raw_results
        if ! raw_results=$(get_pr_list_with_retry "$repo" "$state" "$author" "$limit"); then
            log_error "获取数据失败，等待后重试..."
            sleep $interval
            continue
        fi

        local processed
        processed=$(process_prs_full "$raw_results")

        local summary
        summary=$(generate_summary "$processed")

        format_report "$processed" "$summary" "false"

        log_info "下次刷新: ${interval}s 后..."
        sleep $interval
    done
}

cron_output() {
    local repo="$1"
    local state="$2"
    local author="$3"
    local limit="$4"
    local output_file="$5"

    local raw_results
    if ! raw_results=$(get_pr_list_with_retry "$repo" "$state" "$author" "$limit"); then
        echo "{\"error\": \"failed_to_fetch_pr_list\", \"timestamp\": \"$(date -Iseconds)\"}"
        return 1
    fi

    local processed
    processed=$(process_prs_full "$raw_results")
    local summary
    summary=$(generate_summary "$processed")

    local report
    report=$(jq -n \
        --argjson prs "$processed" \
        --argjson summary "$summary" \
        --arg timestamp "$(date -Iseconds)" \
        '{
            reportType: "pr_monitor",
            generatedAt: $timestamp,
            summary: $summary,
            pullRequests: $prs
        }'
    )

    if [[ -n "$output_file" ]]; then
        echo "$report" > "$output_file"
    else
        echo "$report"
    fi
}

main() {
    local state="$DEFAULT_STATE"
    local repo="$DEFAULT_REPO"
    local author="$DEFAULT_AUTHOR"
    local limit="$DEFAULT_LIMIT"
    local proxy_url=""
    local output_file=""
    local watch_mode_flag=false
    local cron_mode=false

    while [[ $# -gt 0 ]]; do
        case "$1" in
            -s|--state)
                state="$2"
                shift 2
                ;;
            -r|--repo)
                repo="$2"
                shift 2
                ;;
            -a|--author)
                author="$2"
                shift 2
                ;;
            -n|--limit)
                limit="$2"
                shift 2
                ;;
            -p|--proxy)
                proxy_url="$2"
                shift 2
                ;;
            -o|--output)
                output_file="$2"
                shift 2
                ;;
            -j|--json)
                shift
                ;;
            -w|--watch)
                watch_mode_flag=true
                shift
                ;;
            --cron)
                cron_mode=true
                shift
                ;;
            -h|--help)
                usage
                exit 0
                ;;
            *)
                log_error "未知参数: $1"
                usage
                exit 1
                ;;
        esac
    done

    check_dependencies
    setup_proxy "$proxy_url"

    log_info "=== GitHub Bounty Hunter - PR 状态监控 ==="
    log_info "状态筛选: $state"
    [[ -n "$repo" ]] && log_info "仓库: $repo"
    [[ -n "$author" ]] && log_info "作者: $author"
    log_info "结果限制: $limit"

    if [[ "$watch_mode_flag" == true ]]; then
        watch_mode "$repo" "$state" "$author" "$limit"
        return
    fi

    if [[ "$cron_mode" == true ]]; then
        cron_output "$repo" "$state" "$author" "$limit" "$output_file"
        return
    fi

    local raw_results
    if ! raw_results=$(get_pr_list_with_retry "$repo" "$state" "$author" "$limit"); then
        log_error "获取 PR 列表失败"
        exit 1
    fi

    local processed
    processed=$(process_prs_full "$raw_results")

    local summary
    summary=$(generate_summary "$processed")

    local report
    report=$(jq -n \
        --argjson prs "$processed" \
        --argjson summary "$summary" \
        --arg timestamp "$(date -Iseconds)" \
        '{
            reportType: "pr_monitor",
            generatedAt: $timestamp,
            summary: $summary,
            pullRequests: $prs
        }'
    )

    if [[ -n "$output_file" ]]; then
        echo "$report" > "$output_file"
        log_info "报告已保存到: $output_file"
    else
        format_report "$processed" "$summary" "false"
        echo ""
        echo "--- JSON 输出 ---"
        echo "$report" | jq .
    fi

    local total
    total=$(echo "$processed" | jq 'length')
    log_info "共监控 $total 个 PR"
}

main "$@"
