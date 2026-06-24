# GitHub Bounty Hunter 安装指南

## 环境要求

- **操作系统**: Linux (推荐 Ubuntu 22.04+) / macOS / WSL2
- **Python**: 3.10+
- **Bash**: 4.0+
- **GitHub CLI (gh)**: 2.0+
- **jq**: JSON 处理工具

## 快速安装

### 1. 克隆项目

```bash
git clone https://github.com/Ryfferty/github-bounty-hunter.git
cd github-bounty-hunter
```

### 2. 安装 Python 依赖

```bash
pip install -r requirements.txt
```

### 3. 安装系统依赖

**Ubuntu/Debian:**
```bash
sudo apt-get update
sudo apt-get install -y jq gh
```

**macOS:**
```brew install -y jq gh```

### 4. 配置 GitHub 认证

```bash
gh auth login
# 选择 GitHub.com, HTTPS, 浏览器认证
```

验证安装:
```bash
gh auth status
```

## 环境变量配置

创建 `.env` 文件或导出环境变量:

```bash
# GitHub 认证（必需）
export GITHUB_TOKEN="your_github_personal_token"

# IssueHunt API（可选，用于认领赏金）
export ISSUEHUNT_TOKEN="your_issuehunt_token"

# 代理设置（中国用户推荐）
export HTTP_PROXY="http://127.0.0.1:7890"
export HTTPS_PROXY="http://127.0.0.1:7890"
```

## 验证安装

运行测试套件:
```bash
python -m pytest tests/ -v
```

测试赏金发现:
```bash
bash scripts/bounty-scout.sh --help
```

## 目录结构

```
github-bounty-hunter/
├── scripts/               # 核心脚本
│   ├── bounty-scout.sh    # 赏金发现
│   ├── bounty_eval.py     # 赏金评估
│   ├── issuehunt_api.py   # IssueHunt API
│   ├── pr-monitor.sh      # PR 监控
│   ├── workflow.py        # 工作流集成
│   └── config_loader.py   # 配置加载
├── config/                # 配置文件
│   ├── platforms.yaml     # 平台配置
│   └── filters.yaml       # 筛选条件
├── tests/                 # 单元测试
└── docs/                  # 文档
```

## 常见问题

### Q: gh 命令找不到?
A: 确保 `gh` 已安装并在 PATH 中。可通过 `which gh` 检查。

### Q: 代理连接失败?
A: 中国用户建议使用 `--proxy ghfast.top` 或设置本地代理端口。

### Q: API Rate Limited?
A: 脚本已内置限流处理和重试机制。如频繁触发，可增大 `config/platforms.yaml` 中的 `rate_limit` 间隔。
