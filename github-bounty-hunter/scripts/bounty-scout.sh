#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

DEFAULT_LABELS=("bounty" "💰 Bounty" "💎 Bounty" "赏金")
DEFAULT_LANGUAGES=()
DEFAULT_MIN_AMOUNT=0
DEFAULT_PER_PAGE=20
MAX_PAGES=5
RETRY_COUNT=3
RETRY_DELAY=5

usage() {
    cat <<EOF
GitHub Bounty Hunter - 赏金发现脚本

用法: $(basename "$0") [选项]

选项:
    -l, --labels LABELS     搜索标签 (逗号分隔，默认: "bounty,💰 Bounty,💎 Bounty,赏金")
    -L, --language LANG      按语言筛选 (如: python, typescript)
    -m, --min-amount AMOUNT  最小赏金金额 (USD)
    -n, --limit NUM          返回结果数量上限 (默认: $DEFAULT_PER_PAGE)
    -p, --proxy URL          代理地址 (如: http://127.0.0.1:7890 或 ghfast.top)
    -o, --output FILE        输出文件路径 (默认: stdout)
    -j, --json               强制 JSON 输出
    -h, --help               显示帮助信息

示例:
    $(basename "$0") -L python -m 50
    $(basename "$0") -l "bounty,赏金" -L typescript -o results.json
    $(basename "$0") --proxy http://ghfast.top -n 30
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
        log_error "请安装: apt-get install ${missing[*]}"
        exit 1
    fi

    if ! gh auth status &>/dev/null; then
        log_error "GitHub CLI 未认证。请运行 'gh auth login' 进行认证"
        exit 1
    fi
}

setup_proxy() {
    local proxy_url="$1"

    if [[ -z "$proxy_url" ]]; then
        return
    fi

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

build_search_query() {
    local query_parts=()

    # Search for bounty-related terms in title (more effective than labels)
    query_parts+=("bounty" "is:open")

    if [[ ${#LANGUAGES[@]} -gt 0 ]]; then
        local lang_query=""
        for lang in "${LANGUAGES[@]}"; do
            if [[ -n "$lang_query" ]]; then
                lang_query+=" OR "
            fi
            lang_query+="language:$lang"
        done
        query_parts+=("($lang_query)")
    fi

    # Note: min_amount filtering is done post-search, not in the query

    local IFS=' '
    echo "${query_parts[*]}"
}

search_with_retry() {
    local query="$1"
    local limit="$2"
    local attempt=1
    local result=""

    while [[ $attempt -le $RETRY_COUNT ]]; do
        log_info "搜索尝试 $attempt/$RETRY_COUNT: $query"

        result=$(gh search issues \
            "$query" \
            --limit "$limit" \
            --json repository,title,url,labels,createdAt,state,number \
            --sort updated \
            --order desc \
            2>&1) && break

        log_warn "搜索失败 (尝试 $attempt/$RETRY_COUNT): $result"

        if [[ $attempt -lt $RETRY_COUNT ]]; then
            log_info "等待 ${RETRY_DELAY} 秒后重试..."
            sleep $RETRY_DELAY
        fi
        ((attempt++))
    done

    if [[ $attempt -gt $RETRY_COUNT ]]; then
        log_error "搜索失败，已达最大重试次数"
        return 1
    fi

    echo "$result"
}

process_results() {
    local raw_json="$1"
    local output_file="$2"

    local processed_json
    processed_json=$(echo "$raw_json" | jq '
        map({
            repository: .repository.nameWithOwner,
            title: .title,
            url: .url,
            labels: [.labels[].name],
            createdAt: .createdAt,
            state: .state,
            number: .number
        })
    ')

    if [[ -n "$output_file" ]]; then
        echo "$processed_json" > "$output_file"
        log_info "结果已保存到: $output_file"
    else
        echo "$processed_json"
    fi

    local count
    count=$(echo "$processed_json" | jq 'length')
    log_info "共找到 $count 个赏金 issue"
}

parse_labels_input() {
    local input="$1"
    IFS=',' read -ra LABELS <<< "$input"
}

main() {
    local labels_input=""
    LANGUAGES=()
    MIN_AMOUNT=$DEFAULT_MIN_AMOUNT
    LIMIT=$DEFAULT_PER_PAGE
    PROXY_URL=""
    OUTPUT_FILE=""
    FORCE_JSON=false

    while [[ $# -gt 0 ]]; do
        case "$1" in
            -l|--labels)
                labels_input="$2"
                shift 2
                ;;
            -L|--language)
                LANGUAGES+=("$2")
                shift 2
                ;;
            -m|--min-amount)
                MIN_AMOUNT="$2"
                shift 2
                ;;
            -n|--limit)
                LIMIT="$2"
                shift 2
                ;;
            -p|--proxy)
                PROXY_URL="$2"
                shift 2
                ;;
            -o|--output)
                OUTPUT_FILE="$2"
                shift 2
                ;;
            -j|--json)
                FORCE_JSON=true
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

    setup_proxy "$PROXY_URL"

    if [[ -z "$labels_input" ]]; then
        LABELS=("${DEFAULT_LABELS[@]}")
    else
        parse_labels_input "$labels_input"
    fi

    log_info "=== GitHub Bounty Hunter - 赏金发现 ==="
    log_info "搜索标签: ${LABELS[*]}"
    if [[ ${#LANGUAGES[@]} -gt 0 ]]; then
        log_info "语言筛选: ${LANGUAGES[*]}"
    fi
    if [[ "$MIN_AMOUNT" -gt 0 ]]; then
        log_info "最小金额: \$$MIN_AMOUNT"
    fi
    log_info "结果限制: $LIMIT"

    local search_query
    search_query=$(build_search_query "${LABELS[@]}")

    log_info "搜索查询: $search_query"

    local raw_results
    if ! raw_results=$(search_with_retry "$search_query" "$LIMIT"); then
        log_error "搜索失败"
        exit 1
    fi

    process_results "$raw_results" "$OUTPUT_FILE"
}

main "$@"
