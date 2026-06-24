# GitHub Bounty Hunter 工作流程文档

## 完整工作流概览

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│  1. 发现赏金 │ ->│  2. 评估可行性│ ->│  3. 认领 issue│ ->│  4. 提交 PR │
│ bounty-scout │    │ bounty-eval │    │ issuehunt   │    │ git push    │
└─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘
       ↓                   ↓                                    ↓
┌─────────────┐    ┌─────────────┐                    ┌─────────────┐
│  输出:       │    │  输出:       │                    │  5. 监控状态 │
│  JSON 列表  │    │  评分+推荐   │                    │  pr-monitor │
└─────────────┘    └─────────────┘                    └─────────────┘
                                                            ↓
                                                    ┌─────────────┐
                                                    │  6. 收款     │
                                                    │  IssueHunt  │
                                                    └─────────────┘
```

## 步骤详解

### Step 1: 发现赏金

使用 `bounty-scout.sh` 在 GitHub 上搜索带赏金标签的 issue:

```bash
# 基础搜索（默认标签：bounty, 💰 Bounty, 💎 Bounty, 赏金）
bash scripts/bounty-scout.sh

# 按语言筛选
bash scripts/bounty-scout.sh -L python -m 50

# 使用代理 + 输出到文件
bash scripts/bounty-scout.sh --proxy ghfast.top -o bounties.json
```

输出格式:
```json
[
  {
    "repository": "owner/repo",
    "title": "Fix authentication bug",
    "url": "https://github.com/owner/repo/issues/42",
    "labels": ["bounty", "bug"],
    "createdAt": "2024-01-15T10:30:00Z",
    "state": "open"
  }
]
```

### Step 2: 评估可行性

将发现的 issue 导入 `bounty_eval.py` 进行多维度评估:

```python
from scripts.bounty_eval import evaluate_batch, generate_report

issues = json.load(open("bounties.json"))
results = evaluate_batch(issues)
report = generate_report(results)
print(json.dumps(report, indent=2))
```

或命令行:
```bash
python scripts/bounty_eval.py -i bounties.json -o evaluation.json
```

评估维度:
| 维度 | 权重 | 说明 |
|------|------|------|
| 难度 | 25% | 根据关键词和标签判断 |
| 金额 | 25% | 赏金金额分档评分 |
| 竞争 | 15% | 认领人数和 PR 数量 |
| 时效 | 15% | issue 创建时间 |
| 项目质量 | 20% | star 数和维护活跃度 |

推荐等级:
- **🔥 强烈推荐** (≥7.5): 高价值、低竞争、高成功率
- **⭐ 推荐** (≥6.0): 性价比不错
- **💭 可以考虑** (≥4.5): 有一定风险
- **⏭️ 跳过** (<4.5): 不建议投入时间

### Step 3: 认领 Issue

通过 IssueHunt API 认领选定的 issue:

```python
from scripts.issuehunt_api import create_client

client = create_client()
result = client.claim_issue("owner", "repo", 42)
print(result)
```

### Step 4 & 5: 编码与监控

提交代码后，用 `pr-monitor.sh` 监控状态:

```bash
# 单次检查
bash scripts/pr-monitor.sh -s open -a myusername

# 持续监控（每60秒刷新）
bash scripts/pr-monitor.sh --watch

# Cron 定时任务
*/30 * * * * cd /path/to/project && bash scripts/pr-monitor.sh --cron -o pr-status.json
```

监控维度:
- **PR 状态**: OPEN / CLOSED / MERGED
- **CI 状态**: SUCCESS / PENDING / FAILURE
- **Review 状态**: APPROVED / CHANGES_REQUESTED / REVIEW_REQUIRED

### Step 6: 一键完整工作流

使用 `workflow.py` 执行全流程:

```bash
# 完整流程（发现 → 评估 → 推荐）
python scripts/workflow.py -L python -m 50

# 交互模式（可选择查看详情）
python scripts/workflow.py --interactive -n 30

# 输出报告
python scripts/workflow.py -o report.json --verbose
```

## 定时任务配置

### Crontab 示例

```bash
# 编辑 crontab
crontab -e

# 每6小时执行一次完整扫描
0 */6 * * * cd /home/user/github-bounty-hunter && \
    python scripts/workflow.py -o reports/$(date +\%Y\%m\%d_\%H\%M).json >> logs/cron.log 2>&1

# 每30分钟监控PR状态
*/30 * * * * cd /home/user/github-bounty-hunter && \
    bash scripts/pr-monitor.sh --cron -o pr-status.json >> logs/pr_monitor.log 2>&1
```

### systemd Timer（替代方案）

创建 `/etc/systemd/system/bounty-hunter.service`:
```ini
[Unit]
Description=GitHub Bounty Hunter Scanner
After=network.target

[Service]
Type=oneshot
User=bounty-user
WorkingDirectory=/home/user/github-bounty-hunter
ExecStart=/usr/bin/python3 scripts/workflow.py -o report.json
Environment="PATH=/usr/bin:/usr/local/bin"
```

创建 `/etc/systemd/system/bounty-hunter.timer`:
```ini
[Unit]
Description=Run Bounty Hunter every 6 hours

[Timer]
OnCalendar=*-*-* 0,6,12,18:00:00
Persistent=true

[Install]
WantedBy=timers.target
```

启用:
```bash
sudo systemctl enable bounty-hunter.timer
sudo systemctl start bounty-hunter.timer
```

## 最佳实践

1. **优先选择 IssueHunt 平台**: PayPal 支付对中国用户友好
2. **关注新 issue**: 创建 <7 天的 issue 成功率更高
3. **避免高竞争**: 超过 3 人认领的建议跳过
4. **从简单开始**: 先完成 low-hanging fruit 积累信誉
5. **及时响应 review**: 保持活跃度提升后续中标率
