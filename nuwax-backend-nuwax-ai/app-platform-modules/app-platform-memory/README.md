# app-platform-memory

## 模块概述

app-platform-memory 是一个记忆管理模块，为 AI Agent 提供短期、长期和工作记忆的管理能力。

## 功能特性

- **多类型记忆支持**：
  - 短期记忆 (short_term): 用于临时存储对话上下文
  - 长期记忆 (long_term): 持久化存储重要信息
  - 工作记忆 (working): 当前任务相关的活跃记忆

- **记忆管理功能**：
  - 创建、更新、删除记忆
  - 记忆搜索和检索
  - 自动归档过期记忆
  - 重要性评分机制
  - 访问频率统计

- **API 支持**：
  - RESTful API 接口
  - Web 管理界面
  - SDK 集成

## 子模块说明

- `app-platform-memory-spec`: 规范定义和常量
- `app-platform-memory-sdk`: SDK 和 DTO 定义
- `app-platform-memory-domain`: 领域模型和仓储接口
- `app-platform-memory-application`: 应用服务层
- `app-platform-memory-infra`: 基础设施实现
- `app-platform-memory-api`: RESTful API 接口
- `app-platform-memory-web`: Web 管理界面

## 使用方式

### 添加依赖

在需要使用记忆功能的模块中添加依赖：

```xml
<dependency>
    <groupId>com.xspaceagi</groupId>
    <artifactId>app-platform-memory-sdk</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### API 调用示例

#### 创建记忆

```bash
POST /api/memory
Content-Type: application/json

{
  "memoryType": "short_term",
  "content": "用户偏好设置",
  "metadata": {
    "userId": "user123",
    "sessionId": "session456"
  }
}
```

#### 查询记忆

```bash
GET /api/memory/user/{userId}/type/{memoryType}
```

#### 搜索记忆

```bash
GET /api/memory/search?userId={userId}&keyword={keyword}
```

## 配置说明

### 默认配置

- 短期记忆最大容量: 100 条
- 工作记忆最大容量: 50 条
- 长期记忆转换阈值: 7 天

### 自定义配置

可以在配置文件中覆盖默认值：

```yaml
memory:
  short-term:
    max-size: 200
  working:
    max-size: 100
  long-term:
    threshold-days: 14
```

## 技术栈

- Java 17
- Spring Boot
- Lombok
- Maven

## 开发指南

### 构建项目

```bash
mvn clean install
```

### 运行测试

```bash
mvn test
```

## 版本历史

- v1.0.0-SNAPSHOT: 初始版本，提供基础记忆管理功能
