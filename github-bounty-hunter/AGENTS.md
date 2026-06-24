# GitHub Bounty Hunter - AI Agent 自动赏金猎人

## 项目简介

这是一个 AI Agent 自动赏金猎人系统，用于在 GitHub 上自动发现、评估、认领和完成带有赏金的 issue。

**收款方式**: IssueHunt + PayPal（中国用户友好）

## 技术栈

- **语言**: Python 3 + Bash
- **工具**: GitHub CLI (gh), Git, curl
- **平台**: IssueHunt (issuehunt.io)
- **运行环境**: WSL2 (Ubuntu)

## 项目结构

```
github-bounty-hunter/
├── AGENTS.md              # 本文件 - Agent 指令
├── TASKS.md               # 任务队列
├── REVIEW.md              # 审查反馈
├── scripts/               # 核心脚本
│   ├── bounty-scout.sh    # 赏金发现脚本
│   ├── bounty-eval.py     # 赏金评估脚本
│   ├── pr-monitor.sh      # PR 状态监控
│   └── issuehunt-api.py   # IssueHunt API 封装
├── config/                # 配置文件
│   ├── platforms.yaml     # 平台配置
│   └── filters.yaml       # 筛选条件
├── docs/                  # 文档
│   ├── setup.md           # 安装指南
│   └── workflow.md        # 工作流程
└── tests/                 # 测试
    └── test_eval.py       # 评估测试
```

## 核心工作流程

```
1. 发现赏金 → 2. 评估可行性 → 3. 认领 issue → 4. 编写代码 → 5. 提交 PR → 6. 监控状态 → 7. 收款
```

## Agent 指令

### 核心规则

1. **一次只做一个任务**，不要并行
2. **先检查 REVIEW.md**，修复所有 `❌ 未处理` 的审查意见
3. **严格遵循任务顺序**，不要跳过
4. **每完成一个任务**，更新 TASKS.md 状态并提交

### 工作流程

1. **检查审查**: 读取 REVIEW.md，修复所有 `❌ 未处理` 项
2. **找任务**: 读取 TASKS.md，找到当前或下一个任务
3. **执行**: 按任务要求实现功能
4. **验证**: 运行测试确保通过
5. **提交**: 一个任务一个提交，格式: `feat: [TASK-XXX] 描述`
6. **更新状态**: 在 TASKS.md 中标记任务完成

### Git 提交格式

```
feat: [TASK-XXX] 简短描述
fix: [TASK-XXX] 修复 XXX 问题
docs: [TASK-XXX] 更新文档
test: [TASK-XXX] 添加测试
```

### 代码规范

- 使用 Python 3.10+ 语法
- 函数和变量使用 snake_case
- 类名使用 PascalCase
- 常量使用 UPPER_SNAKE_CASE
- 所有公开函数必须有 docstring
- 错误处理要完善，不能吞掉异常

### 测试要求

- 每个新功能都要有对应测试
- 测试覆盖率目标: 80%+
- 运行测试: `python -m pytest tests/ -v`

### 重要提醒

- **不要修改 AGENTS.md**（只有人类审查者可以修改）
- **遇到阻塞**：在 TASKS.md 中标记 `🚫 阻塞`，在 REVIEW.md 中说明原因
- **网络问题**：使用 ghfast.top 代理访问 GitHub
- **API 密钥**：不要提交到代码中，使用环境变量

## 支持的赏金平台

### IssueHunt (首选)

- **网址**: https://issuehunt.io
- **支付**: PayPal（中国用户友好）
- **典型赏金**: $20-$200
- **特点**: 专门做 issue 赏金，界面友好

### GitHub 搜索

- **标签**: `bounty`, `💎 Bounty`, `赏金`
- **搜索命令**: `gh search issues 'label:bounty is:open'`

## 赏金评估标准

AI Agent 应评估以下因素：

1. **难度**: issue 描述是否清晰，是否在 Agent 能力范围内
2. **金额**: 是否值得投入时间（建议 >$20）
3. **竞争**: 是否已有人认领或提交 PR
4. **时效**: issue 创建时间，是否还活跃（建议 <30 天）
5. **项目质量**: star 数（建议 >100）、维护活跃度

## 构建和测试

```bash
# 安装依赖
pip install -r requirements.txt

# 运行测试
python -m pytest tests/ -v

# 运行赏金发现
bash scripts/bounty-scout.sh

# 运行赏金评估
python scripts/bounty-eval.py
```

## 环境变量

```bash
# GitHub 认证
export GITHUB_TOKEN="your_github_token"

# IssueHunt API (可选)
export ISSUEHUNT_TOKEN="your_issuehunt_token"

# 代理设置 (中国用户)
export HTTP_PROXY="http://127.0.0.1:7890"
export HTTPS_PROXY="http://127.0.0.1:7890"
```
