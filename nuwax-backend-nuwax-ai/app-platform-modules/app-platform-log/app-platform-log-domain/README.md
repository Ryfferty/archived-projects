# LogHttpClientAdapter 使用说明

## 概述

`LogHttpClientAdapter` 是一个用于与 Rust 日志服务进行 HTTP 通信的客户端适配器。它提供了与 Rust 工程接口一一对应的 HTTP 请求方法，支持智能体日志的新增、批量新增和搜索功能。

## 功能特性

- **健康检查**：检查 Rust 服务的健康状态
- **就绪检查**：检查 Rust 服务是否就绪
- **日志新增**：支持单个和批量新增智能体日志
- **日志搜索**：支持条件查询、分页和排序
- **异步支持**：所有方法都提供异步和同步两个版本
- **配置化**：通过配置文件管理连接参数
- **配置验证**：启动时验证必需配置，缺失时抛出异常

## 配置说明

在主配置文件（如 `application-dev.yml`）中添加以下配置：

```yaml
log-module:
  log:
    rust-service:
      # Rust 日志服务的基础 URL（必填）
      base-url: http://localhost:8098
      # 连接超时时间（秒），默认 10
      connect-timeout-seconds: 10
      # 写入超时时间（秒），默认 30
      write-timeout-seconds: 30
      # 读取超时时间（秒），默认 30
      read-timeout-seconds: 30
      # 是否启用连接池，默认 true
      enable-connection-pool: true
      # 最大空闲连接数，默认 5
      max-idle-connections: 5
      # 连接保持存活时间（分钟），默认 5
      keep-alive-duration-minutes: 5

# 日志级别配置（复用现有的 log.level 配置）
log:
  level:
    com.xspaceagi.domain.service.LogHttpClientAdapter: DEBUG
```

**注意**：`base-url` 是必填配置项，如果未配置或为空，应用启动时会抛出异常。

## 使用示例

### 1. 依赖注入

```java
@Service
public class LogService {
    
    @Autowired
    private LogHttpClientAdapter logHttpClientAdapter;
    
    // ... 业务方法
}
```

### 2. 健康检查

```java
// 异步方式
CompletableFuture<Boolean> healthFuture = logHttpClientAdapter.healthCheck();
Boolean isHealthy = healthFuture.get();

// 同步方式（不推荐在高并发场景使用）
Boolean isHealthy = logHttpClientAdapter.healthCheck().join();
```

### 3. 新增单个日志

```java
AgentLogEntry logEntry = AgentLogEntry.builder()
    .requestId("req_001")
    .conversationId("session_001")
    .userUid("user_123")
    .tenantId("tenant_001")
    .userInput("用户输入内容")
    .output("系统输出内容")
    .inputToken(50)
    .outputToken(100)
    .requestStartTime(Instant.now().minusSeconds(5))
    .requestEndTime(Instant.now())
    .elapsedTimeMs(5000L)
    .status("success")
    .build();

// 异步方式
CompletableFuture<Boolean> result = logHttpClientAdapter.addAgentLog(logEntry);

// 同步方式
boolean success = logHttpClientAdapter.addAgentLogSync(logEntry);
```

### 4. 批量新增日志

```java
List<AgentLogEntry> logEntries = Arrays.asList(
    AgentLogEntry.builder()...build(),
    AgentLogEntry.builder()...build()
);

// 异步方式
CompletableFuture<Boolean> result = logHttpClientAdapter.batchAddAgentLogs(logEntries);

// 同步方式
boolean success = logHttpClientAdapter.batchAddAgentLogsSync(logEntries);
```

### 5. 搜索日志

```java
// 构建搜索条件
AgentLogSearchParams searchParams = AgentLogSearchParams.builder()
    .tenantId("tenant_001")
    .userUid("user_123")
    .userInput("搜索关键词")
    .startTime(Instant.now().minus(Duration.ofDays(1)))
    .endTime(Instant.now())
    .build();

// 构建搜索请求
AgentLogSearchRequest searchRequest = AgentLogSearchRequest.builder()
    .queryFilter(searchParams)
    .current(1L)
    .pageSize(20L)
    .orders(Arrays.asList(
        AgentLogSearchRequest.OrderBy.builder()
            .column("request_start_time")
            .asc(false)
            .build()
    ))
    .build();

// 异步搜索
CompletableFuture<AgentLogSearchResult> future = logHttpClientAdapter.searchAgentLogs(searchRequest);
AgentLogSearchResult result = future.get();

// 同步搜索
AgentLogSearchResult result = logHttpClientAdapter.searchAgentLogsSync(searchRequest);

// 处理搜索结果
System.out.println("总记录数: " + result.getTotal());
System.out.println("当前页: " + result.getCurrent());
System.out.println("每页大小: " + result.getSize());
result.getRecords().forEach(log -> {
    System.out.println("日志ID: " + log.getRequestId());
    System.out.println("用户输入: " + log.getUserInput());
});
```

## 模型类说明

### AgentLogEntry
智能体日志条目，包含以下主要字段：
- `requestId`: 请求ID（必填）
- `conversationId`: 会话ID（必填）
- `userUid`: 用户UID（必填）
- `tenantId`: 租户ID（必填）
- `spaceId`: 空间ID（可选）
- `userInput`: 用户输入内容
- `output`: 系统输出内容
- `inputToken/outputToken`: Token 数量
- `requestStartTime/requestEndTime`: 请求时间
- `elapsedTimeMs`: 耗时（毫秒）
- `nodeType/nodeName/status`: 节点信息

### AgentLogSearchParams
搜索参数，支持多条件组合查询：
- 基础过滤：`requestId`, `conversationId`, `userUid`, `tenantId`, `spaceId`
- 全文搜索：`userInput`, `output`
- 时间范围：`startTime`, `endTime`

### AgentLogSearchRequest
搜索请求包装类：
- `queryFilter`: 查询条件
- `current`: 当前页码
- `pageSize`: 每页大小
- `orders`: 排序条件列表

### AgentLogSearchResult
搜索结果：
- `records`: 日志记录列表
- `total`: 总记录数
- `current`: 当前页
- `size`: 每页大小
- `elapsedTimeMs`: 查询耗时

## 错误处理

### 配置验证
客户端适配器在启动时会验证配置：
- 如果 `base-url` 未配置或为空，会抛出 `IllegalArgumentException`
- 错误信息会记录到日志中

### 运行时异常
客户端适配器已经内置了基本的错误处理：
- 网络异常会被记录并返回 false 或抛出异常
- HTTP 错误状态码会被记录并处理
- JSON 序列化/反序列化错误会被捕获

在业务代码中建议使用 try-catch 块处理异常：

```java
try {
    AgentLogSearchResult result = logHttpClientAdapter.searchAgentLogsSync(searchRequest);
    // 处理搜索结果
} catch (Exception e) {
    log.error("搜索日志失败", e);
    // 处理错误情况
}
```

## 注意事项

1. **必填配置**：`log-module.log.rust-service.base-url` 是必填配置，不能为空
2. **服务依赖**：确保 Rust 日志服务正在运行并可访问
3. **线程安全**：`LogHttpClientAdapter` 是线程安全的，可以在多线程环境中使用
4. **资源管理**：应用关闭时会自动清理 HTTP 客户端资源
5. **配置优化**：根据实际网络环境调整超时时间和连接池参数
6. **日志级别**：通过 `log.level` 配置控制日志输出级别

## 测试

运行单元测试前确保：
1. Rust 服务正在运行
2. 配置正确的服务地址

```bash
# 运行特定测试类
mvn test -Dtest=LogHttpClientAdapterTest

# 运行所有测试
mvn test
```

## 配置示例

### 开发环境配置
```yaml
log-module:
  log:
    rust-service:
      base-url: http://localhost:8098
      
log:
  level:
    com.xspaceagi.domain.service.LogHttpClientAdapter: DEBUG
```

### 生产环境配置
```yaml
log-module:
  log:
    rust-service:
      base-url: http://rust-log-service:8098
      connect-timeout-seconds: 5
      write-timeout-seconds: 60
      read-timeout-seconds: 60
      max-idle-connections: 10
      
log:
  level:
    com.xspaceagi.domain.service.LogHttpClientAdapter: INFO
``` 