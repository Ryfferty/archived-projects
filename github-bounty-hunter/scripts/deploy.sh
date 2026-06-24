#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

LOG_DIR="${PROJECT_ROOT}/logs"
REPORT_DIR="${PROJECT_ROOT}/reports"
PID_FILE="${PROJECT_ROOT}/.bounty_hunter.pid"

DEFAULT_SCAN_INTERVAL_HOURS=6
DEFAULT_MONITOR_INTERVAL_MINUTES=30
MAX_LOG_FILES=30
MAX_REPORT_FILES=50

usage() {
    cat <<EOF
GitHub Bounty Hunter - 自动化部署脚本

用法: $(basename "$0") [命令] [选项]

命令:
    install             初始化环境和依赖
    setup-cron           配置定时任务
    remove-cron          移除定时任务
    run-scan             执行一次完整扫描
    run-monitor          执行一次 PR 监控
    status               查看运行状态
    stop                 停止所有进程
    logs [lines]         查看最近日志 (默认 50 行)
    cleanup              清理旧日志和报告
    health-check        运行健康检查

选项:
    --scan-interval HOURS      扫描间隔小时数 (默认: $DEFAULT_SCAN_INTERVAL_HOURS)
    --monitor-interval MIN     监控间隔分钟数 (默认: $DEFAULT_MONITOR_INTERVAL_MINUTES)
    --dry-run                   仅显示将执行的操作，不实际执行
    -h, --help                  显示帮助信息

示例:
    $(basename "$0") install
    $(basename "$0") setup-cron --scan-interval 4
    $(basename "$0") run-scan
    $(basename "$0") logs 100
EOF
}

log() {
    local level="$1"
    shift
    local timestamp
    timestamp=$(date '+%Y-%m-%d %H:%M:%S')
    echo "[${timestamp}] [${level}] $*" | tee -a "${LOG_DIR}/deploy.log"
}

log_info() { log "INFO" "$@"; }
log_error() { log "ERROR" "$@"; }
log_warn() { log "WARN" "$@"; }

ensure_dirs() {
    mkdir -p "$LOG_DIR"
    mkdir -p "$REPORT_DIR"
}

install_deps() {
    log_info "检查并安装 Python 依赖..."
    if command -v pip &>/dev/null; then
        pip install -r "${PROJECT_ROOT}/requirements.txt" -q 2>/dev/null || true
    fi

    log_info "检查系统依赖..."
    local missing=()
    for cmd in gh jq python3; do
        if ! command -v "$cmd" &>/dev/null; then
            missing+=("$cmd")
        fi
    done

    if [[ ${#missing[@]} -gt 0 ]]; then
        log_warn "缺少系统依赖: ${missing[*]}"
        log_warn "请手动安装: sudo apt-get install ${missing[*]}"
    fi

    log_info "依赖检查完成"
}

setup_cron() {
    local scan_interval=${1:-$DEFAULT_SCAN_INTERVAL_HOURS}
    local monitor_interval=${2:-$DEFAULT_MONITOR_INTERVAL_MINUTES}

    log_info "配置定时任务..."

    local cron_entry_scan="0 */${scan_interval} * * * cd ${PROJECT_ROOT} && python3 scripts/workflow.py -o ${REPORT_DIR}/scan_\$(date +\\%Y\\%m\\%d_\\%H\\%M).json >> ${LOG_DIR}/cron.log 2>&1"
    local cron_entry_monitor="*/${monitor_interval} * * * * cd ${PROJECT_ROOT} && bash scripts/pr-monitor.sh --cron -o ${REPORT_DIR}/pr_status.json >> ${LOG_DIR}/monitor.log 2>&1"

    (crontab -l 2>/dev/null | grep -v "github-bounty-hunter"; echo "# GitHub Bounty Hunter - 扫描"; echo "$cron_entry_scan"; echo "# GitHub Bounty Hunter - PR监控"; echo "$cron_entry_monitor") | crontab -

    log_info "Crontab 配置完成:"
    log_info "  扫描周期: 每 ${scan_interval} 小时"
    log_info "  监控周期: 每 ${monitor_interval} 分钟"
    log_info "  日志目录: ${LOG_DIR}"
    log_info "  报告目录: ${REPORT_DIR}"

    crontab -l | grep -A1 "github-bounty-hunter"
}

remove_cron() {
    log_info "移除定时任务..."
    (crontab -l 2>/dev/null | grep -v "github-bounty-hunter") | crontab -
    log_info "Cron 任务已移除"
}

run_scan() {
    ensure_dirs
    local timestamp
    timestamp=$(date +%Y%m%d_%H%M%S)
    local output_file="${REPORT_DIR}/scan_${timestamp}.json"

    log_info "开始执行扫描... 输出: ${output_file}"

    if ! python3 "${PROJECT_ROOT}/scripts/workflow.py" -o "$output_file"; then
        log_error "扫描执行失败"
        return 1
    fi

    log_info "扫描完成: ${output_file}"
}

run_monitor() {
    ensure_dirs
    local output_file="${REPORT_DIR}/pr_status.json"

    log_info "开始 PR 监控..."
    if ! bash "${PROJECT_ROOT}/scripts/pr-monitor.sh" --cron -o "$output_file"; then
        log_error "监控执行失败"
        return 1
    fi

    log_info "监控完成: ${output_file}"
}

show_status() {
    echo ""
    echo "=== GitHub Bounty Hunter 运行状态 ==="
    echo ""

    echo "📁 项目路径: ${PROJECT_ROOT}"
    echo "📂 日志目录: ${LOG_DIR}"
    echo "📂 报告目录: ${REPORT_DIR}"
    echo ""

    if [[ -f "$PID_FILE" ]]; then
        echo "🔄 PID 文件存在: $(cat "$PID_FILE")"
    else
        echo "⏸️ 无运行中的 PID 文件"
    fi
    echo ""

    echo "📋 Cron 任务:"
    if crontab -l 2>/dev/null | grep -q "github-bounty-hunter"; then
        crontab -l 2>/dev/null | grep "github-bounty-hunter" | head -5
    else
        echo "  (无)"
    fi
    echo ""

    echo "📊 最近报告:"
    ls -lt "$REPORT_DIR"/*.json 2>/dev/null | head -5 || echo "  (无)"
    echo ""

    echo "📝 日志大小:"
    du -sh "$LOG_DIR"/* 2>/dev/null || echo "  (空)"
    echo ""

    echo "🧩 依赖状态:"
    for cmd in gh jq python3; do
        if command -v "$cmd" &>/dev/null; then
            version=$("$cmd" --version 2>/dev/null | head -1 || echo "installed")
            echo "  ✅ $cmd: $version"
        else
            echo "  ❌ $cmd: 未安装"
        fi
    done
    echo ""

    echo "🔑 认证状态:"
    if gh auth status &>/dev/null 2>&1; then
        echo "  ✅ GitHub CLI: 已认证"
    else
        echo "  ❌ GitHub CLI: 未认证"
    fi

    if [[ -n "${ISSUEHUNT_TOKEN:-}" ]]; then
        echo "  ✅ IssueHunt Token: 已配置"
    else
        echo "  ⚠️ IssueHunt Token: 未配置 (可选)"
    fi
}

show_logs() {
    local lines="${1:-50}"
    ensure_dirs

    echo ""
    echo "=== 最近 ${lines} 行部署日志 ==="
    echo ""

    tail -n "$lines" "${LOG_DIR}/deploy.log" 2>/dev/null || echo "(无日志)"
    echo ""

    echo "--- 最近 Cron 错误 ---"
    grep -i "error\|failed\|traceback" "${LOG_DIR}/cron.log" 2>/dev/null | tail -10 || echo "(无错误)"
}

cleanup() {
    log_info "清理旧文件..."

    log_info "清理日志 (保留最新 ${MAX_LOG_FILES} 个)... "
    find "$LOG_DIR" -name "*.log" -type f 2>/dev/null | sort | head -n -"$MAX_LOG_FILES" | xargs rm -f 2>/dev/null || true

    log_info "清理报告 (保留最新 ${MAX_REPORT_FILES} 个)... "
    find "$REPORT_DIR" -name "*.json" -type f 2>/dev/null | sort | head -n -"$MAX_REPORT_FILES" | xargs rm -f 2>/dev/null || true

    log_info "清理完成"
}

health_check() {
    echo ""
    echo "=== 健康检查 ==="
    echo ""

    local failures=0

    echo -n "  Python 依赖: "
    if python3 -c "import requests, yaml, pytest" 2>/dev/null; then
        echo "✅ OK"
    else
        echo "❌ FAIL"; ((failures++))
    fi

    echo -n "  测试套件: "
    local test_result
    test_result=$(cd "$PROJECT_ROOT" && python3 -m pytest tests/ -q --tb=no 2>&1 | tail -3)
    if echo "$test_result" | grep -q "passed"; then
        passed=$(echo "$test_result" | grep -oP '\d+(?= passed)')
        echo "✅ ${passed} tests passed"
    else
        echo "❌ FAIL"; ((failures++))
    fi

    echo -n "  配置文件: "
    if [[ -f "${PROJECT_ROOT}/config/platforms.yaml" ]] && [[ -f "${PROJECT_ROOT}/config/filters.yaml" ]]; then
        echo "✅ OK"
    else
        echo "❌ FAIL"; ((failures++))
    fi

    echo -n "  脚本可执行: "
    local script_ok=true
    for script in bounty-scout.sh pr-monitor.sh; do
        if [[ -x "${PROJECT_ROOT}/scripts/$script" ]]; then
            : 
        else
            script_ok=false
        fi
    done
    if $script_ok; then
        echo "✅ OK"
    else
        echo "❌ FAIL"; ((failures++))
    fi

    echo -n "  磁盘空间: "
    local disk_pct
    disk_pct=$(df "$PROJECT_ROOT" | awk 'NR==2{print $5}')
    local disk_num
    disk_num="${disk_pct%\%}"
    if [[ "$disk_num" -lt 90 ]]; then
        echo "✅ ${disk_pct} used"
    else
        echo "⚠️ ${disk_pct} used (接近满)"; ((failures++))
    fi

    echo ""
    if [[ "$failures" -eq 0 ]]; then
        echo "🟢 所有检查通过"
        return 0
    else
        echo "🔴 ${failures} 个检查失败"
        return 1
    fi
}

stop_all() {
    log_info "停止所有 Bounty Hunter 进程..."
    pkill -f "scripts/workflow.py" 2>/dev/null && log_info "已停止 workflow 进程" || true
    pkill -f "scripts/pr-monitor.*watch" 2>/dev/null && log_info "已停止 monitor 进程" || true

    if [[ -f "$PID_FILE" ]]; then
        rm -f "$PID_FILE"
        log_info "PID 文件已清理"
    fi
}

main() {
    local dry_run=false
    local scan_interval=$DEFAULT_SCAN_INTERVAL_HOURS
    local monitor_interval=$DEFAULT_MONITOR_INTERVAL_MINUTES
    local command=""
    local log_lines=50

    while [[ $# -gt 0 ]]; do
        case "$1" in
            install|setup-cron|remove-cron|run-scan|run-monitor|status|stop|logs|cleanup|health-check)
                command="$1"
                shift
                ;;
            --scan-interval)
                scan_interval="$2"
                shift 2
                ;;
            --monitor-interval)
                monitor_interval="$2"
                shift 2
                ;;
            --dry-run)
                dry_run=true
                shift
                ;;
            logs)
                command="logs"
                shift
                if [[ $# -gt 0 && "$1" =~ ^[0-9]+$ ]]; then
                    log_lines="$1"
                    shift
                fi
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

    if [[ -z "$command" ]]; then
        usage
        exit 0
    fi

    ensure_dirs

    case "$command" in
        install)
            install_deps
            ;;
        setup-cron)
            if $dry_run; then
                echo "[DRY-RUN] 将配置 cron:"
                echo "  扫描: 每 ${scan_interval}h"
                echo "  监控: 每 ${monitor_interval}min"
            else
                setup_cron "$scan_interval" "$monitor_interval"
            fi
            ;;
        remove-cron)
            if $dry_run; then
                echo "[DRY-RUN] 将移除所有 github-bounty-hunter cron 条目"
            else
                remove_cron
            fi
            ;;
        run-scan)
            run_scan
            ;;
        run-monitor)
            run_monitor
            ;;
        status)
            show_status
            ;;
        stop)
            stop_all
            ;;
        logs)
            show_logs "$log_lines"
            ;;
        cleanup)
            cleanup
            ;;
        health-check)
            health_check
            ;;
    esac
}

main "$@"
