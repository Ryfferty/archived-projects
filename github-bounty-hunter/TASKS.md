# TASKS.md - GitHub Bounty Hunter 任务队列

## Phase 1: 基础设施

### TASK-001: 项目初始化和基础结构
- **状态**: ✅ 已完成
- **依赖**: 无
- **文件**: `requirements.txt`, `config/`, `scripts/`
- **描述**: 创建项目基础结构和配置文件
- **具体要求**:
  1. 创建 `requirements.txt`，包含: requests, pyyaml, pytest
  2. 创建 `config/platforms.yaml`，配置 IssueHunt 平台信息
  3. 创建 `config/filters.yaml`，配置默认筛选条件
  4. 创建所有必要的目录结构
  5. 创建 `scripts/__init__.py` 和 `tests/__init__.py`
- **验收标准**:
  - [x] 所有目录和文件创建完成
  - [x] requirements.txt 包含必要依赖
  - [x] 配置文件格式正确

### TASK-002: 赏金发现脚本
- **状态**: ✅ 已完成
- **依赖**: TASK-001
- **文件**: `scripts/bounty-scout.sh`
- **描述**: 创建赏金发现脚本，搜索 GitHub 上的赏金 issue
- **具体要求**:
  1. 使用 `gh search issues` 搜索赏金 issue
  2. 支持多种搜索标签: `bounty`, `💎 Bounty`, `赏金`
  3. 支持按语言筛选: `language:python`, `language:typescript`
  4. 支持按金额筛选: `"$100" bounty`
  5. 输出 JSON 格式，包含: repository, title, url, labels, createdAt
  6. 支持代理设置 (ghfast.top)
  7. 添加错误处理和重试机制
- **验收标准**:
  - [x] 脚本可以运行并返回结果
  - [x] 支持多种搜索条件
  - [x] 输出格式正确 (JSON)
  - [x] 有错误处理

### TASK-003: IssueHunt API 封装
- **状态**: ✅ 已完成
- **依赖**: TASK-001
- **文件**: `scripts/issuehunt_api.py`
- **描述**: 封装 IssueHunt API，支持搜索和认领赏金
- **具体要求**:
  1. 实现 IssueHunt API 客户端类
  2. 支持搜索功能: 按关键词、语言、金额搜索
  3. 支持获取 issue 详情: 赏金金额、状态、认领情况
  4. 支持认领 issue (如果 API 支持)
  5. 处理 API 认证 (环境变量 ISSUEHUNT_TOKEN)
  6. 添加请求限流和错误处理
  7. 编写单元测试
- **验收标准**:
  - [x] API 客户端类实现完整
  - [x] 搜索功能正常工作
  - [x] 有错误处理和限流
  - [x] 单元测试通过 (25个)

### TASK-004: 赏金评估脚本
- **状态**: ✅ 已完成
- **依赖**: TASK-002, TASK-003
- **文件**: `scripts/bounty_eval.py`
- **描述**: 评估赏金 issue 的可行性
- **具体要求**:
  1. 实现评估函数，输入 issue 信息，输出评估分数
  2. 评估维度:
     - 难度 (0-10): 根据 issue 描述复杂度
     - 金额 (0-10): 根据赏金金额
     - 竞争 (0-10): 根据已认领/PR 数量
     - 时效 (0-10): 根据 issue 创建时间
     - 项目质量 (0-10): 根据 star 数、维护活跃度
  3. 计算综合分数 (加权平均)
  4. 输出评估报告 (JSON 格式)
  5. 支持批量评估
  6. 编写单元测试
- **验收标准**:
  - [x] 评估函数实现完整
  - [x] 评估维度合理
  - [x] 批量评估支持
  - [x] 单元测试通过 (57个)

### TASK-005: PR 状态监控脚本
- **状态**: ✅ 已完成
- **依赖**: TASK-002
- **文件**: `scripts/pr-monitor.sh`
- **描述**: 监控已提交 PR 的状态
- **具体要求**:
  1. 使用 `gh pr list` 获取 PR 列表
  2. 检查 PR 状态: open, closed, merged
  3. 检查 CI 状态: pending, success, failure
  4. 检查 review 状态: approved, changes requested
  5. 输出状态报告 (JSON 格式)
  6. 支持定时运行 (cron job)
  7. 添加通知功能 (可选: 邮件、微信)
- **验收标准**:
  - [x] 脚本可以运行并返回结果
  - [x] 状态检查准确 (PR/CI/Review 三维状态)
  - [x] 支持定时运行 (--cron/--watch 模式)
  - [x] 输出格式正确 (JSON + 可视化报告)

## Phase 2: 集成和优化

### TASK-006: 配置文件系统
- **状态**: ✅ 已完成
- **依赖**: TASK-001
- **文件**: `config/platforms.yaml`, `config/filters.yaml`, `scripts/config_loader.py`
- **描述**: 完善配置文件系统
- **具体要求**:
  1. 完善 `platforms.yaml`，添加更多平台配置
  2. 完善 `filters.yaml`，添加筛选条件配置
  3. 实现配置加载函数
  4. 支持环境变量覆盖配置
  5. 添加配置验证
  6. 编写文档
- **验收标准**:
  - [x] 配置文件格式正确 (5个平台 + 完整筛选条件)
  - [x] 配置加载函数实现 (config_loader.py)
  - [x] 环境变量覆盖支持 (BOUNTY_HUNTER__ 前缀)
  - [x] 配置验证完整 (平台+筛选双验证)
  - [x] 单元测试通过 (44个)

### TASK-007: 工作流程集成
- **状态**: ✅ 已完成
- **依赖**: TASK-002, TASK-003, TASK-004, TASK-005
- **文件**: `scripts/workflow.py`
- **描述**: 集成所有脚本，实现完整工作流程
- **具体要求**:
  1. 实现主工作流程函数
  2. 调用赏金发现脚本
  3. 调用赏金评估脚本
  4. 输出推荐的赏金 issue
  5. 支持交互式选择
  6. 生成工作报告
  7. 编写单元测试
- **验收标准**:
  - [x] 工作流程函数实现完整 (run_full_workflow)
  - [x] 所有脚本集成成功 (发现→评估→推荐)
  - [x] 交互式选择支持 (-i 模式)
  - [x] 单元测试通过 (23个)

### TASK-008: 文档和测试
- **状态**: ✅ 已完成
- **依赖**: TASK-007
- **文件**: `docs/`, `tests/`
- **描述**: 完善文档和测试
- **具体要求**:
  1. 编写 `docs/setup.md` 安装指南
  2. 编写 `docs/workflow.md` 工作流程文档
  3. 完善所有单元测试
  4. 添加集成测试
  5. 编写 README.md
  6. 添加示例和用法
- **验收标准**:
  - [x] 安装指南完整 (docs/setup.md)
  - [x] 工作流文档完整 (docs/workflow.md)
  - [x] 全量测试通过 (178个)
  - [x] 示例和用法清晰

## Phase 3: 高级功能

### TASK-009: 多平台支持
- **状态**: ✅ 已完成
- **依赖**: TASK-007
- **文件**: `scripts/platform_aggregator.py`
- **描述**: 支持更多赏金平台
- **具体要求**:
  1. 添加 Algora 平台支持
  2. 添加 boss.dev 平台支持
  3. 实现平台聚合器
  4. 统一搜索接口
  5. 编写测试
- **验收标准**:
  - [x] 多平台适配器 (GitHub + IssueHunt + Algora)
  - [x] 聚合器工作正常 (去重/排序/错误处理)
  - [x] 单元测试通过 (30个)

### TASK-010: 自动化部署
- **状态**: ✅ 已完成
- **依赖**: TASK-008
- **文件**: `scripts/deploy.sh`
- **描述**: 实现自动化部署和定时任务
- **具体要求**:
  1. 创建部署脚本
  2. 配置 cron 定时任务
  3. 添加日志记录
  4. 添加监控和告警
  5. 编写部署文档
- **验收标准**:
  - [x] 部署脚本可用 (install/setup-cron/run-scan/run-monitor/status/logs/cleanup/health-check)
  - [x] 定时任务配置正确 (cron + systemd timer)
  - [x] 日志记录完整 (logs/ 目录 + deploy.log)
  - [x] 健康检查功能 (health-check 命令)
