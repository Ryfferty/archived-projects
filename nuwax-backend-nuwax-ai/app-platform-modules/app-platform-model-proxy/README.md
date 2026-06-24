# Model Proxy 模块

大模型请求代理模块，用于代理和管理大模型 API 请求。

## 功能特性

1. **API Key 代理**：用户使用自己的 API Key，后端代理到实际的大模型服务
2. **配置管理**：通过 HTTP API 管理 API Key 配置
3. **高性能**：基于 Netty 实现的高性能 HTTP/HTTPS 代理
4. **缓存支持**：使用本地缓存提高查询性能

## 架构设计

```
┌─────────────────┐      ┌──────────────────┐      ┌─────────────────┐
│   Client        │ ───> │  Model Proxy     │ ───> │  LLM Provider   │
│   (User API Key)│      │  Server          │      │  (Backend API)  │
└─────────────────┘      └──────────────────┘      └─────────────────┘
                               │
                               ▼
                        ┌──────────────────┐
                        │  Configuration   │
                        │  Service        │
                        └──────────────────┘
```

## 核心组件

### 1. ModelProxyServer
代理服务器启动类，基于 Netty 实现 HTTP/HTTPS 代理服务。

**配置项：**
- `model.proxy.port`: 代理服务端口（默认：8088）
- `model.proxy.ssl.enabled`: 是否启用 SSL（默认：false）

### 2. HttpProxyRequestHandler
HTTP 请求处理器，负责：
- 提取用户 API Key
- 查询代理配置
- 转发请求到后端服务
- 转发响应到客户端

### 3. ModelApiProxyConfigService
配置管理服务，提供：
- 根据用户 API Key 查询配置
- 添加/更新配置
- 删除配置
- 根据 ID 查询配置

### 4. ModelApiProxyConfigController
HTTP API 控制器，提供 RESTful 接口：
- `GET /api/model/proxy/config/query?userApiKey={key}` - 查询配置
- `GET /api/model/proxy/config/get/{id}` - 根据 ID 查询
- `POST /api/model/proxy/config/save` - 保存/更新配置
- `DELETE /api/model/proxy/config/delete/{id}` - 删除配置

## 使用方法

### 1. 配置代理服务

在 `application.yml` 中配置：

```yaml
model:
  proxy:
    port: 8088
    ssl:
      enabled: false
```

### 2. 添加代理配置

调用 API 添加配置：

```bash
curl -X POST http://localhost:8080/api/model/proxy/config/save \
  -H "Content-Type: application/json" \
  -d '{
    "userApiKey": "sk-user-xxx",
    "baseUrl": "https://api.openai.com/v1",
    "backendApiKey": "sk-backend-yyy",
    "model": "gpt-4",
    "enabled": true,
    "description": "OpenAI GPT-4 代理"
  }'
```

### 3. 使用代理

客户端使用用户 API Key 调用大模型：

```bash
curl -X POST http://localhost:8088/api/proxy/model/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer sk-user-xxx" \
  -d '{
    "model": "gpt-4",
    "messages": [{"role": "user", "content": "Hello!"}]
  }'
```

代理服务器会：
1. 提取 `Authorization` 或 `x-api-key` 头中的用户 API Key
2. 查询对应的代理配置
3. 将请求转发到后端服务（使用后端 API Key）
4. 将响应返回给客户端

## 数据库表

```sql
CREATE TABLE `model_api_proxy_config` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
    `tenant_id` BIGINT(20) NOT NULL,
    `user_api_key` VARCHAR(255) NOT NULL,
    `base_url` VARCHAR(512) NOT NULL,
    `backend_api_key` VARCHAR(255) NOT NULL,
    `model` VARCHAR(128) DEFAULT NULL,
    `enabled` TINYINT(1) NOT NULL DEFAULT 1,
    `description` VARCHAR(512) DEFAULT NULL,
    `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_user_api_key` (`tenant_id`, `user_api_key`)
);
```

## 接口文档

启动服务后访问 Swagger 文档：http://localhost:8080/swagger-ui.html

## 注意事项

1. 代理服务器默认使用 HTTP，如需 HTTPS 请配置 SSL 证书
2. 用户 API Key 需要在租户内唯一
3. 配置信息会缓存 5 分钟，修改配置后即时生效
4. 支持 WebSocket 连接升级
