# 生态市场模块开发规则

## 1. 模块概述

生态市场模块是一个用于管理和分享插件、模板和MCP的平台，支持服务器端和客户端两种模式。服务器端负责集中管理配置和审核，客户端负责使用和提交配置。

## 2. 架构设计

### 2.1 模式选择

生态市场支持两种模式：
- **服务器模式**：作为中心节点，管理所有配置和审核流程
- **客户端模式**：作为使用节点，从服务器获取配置并提交配置审核

通过配置文件中的 `eco-market.mode.type` 属性设置，可选值为 `server` 或 `client`。

### 2.2 分层结构

遵循DDD设计原则，模块分为以下几层：
- **领域层**：核心业务逻辑，包括领域模型和领域服务
- **应用层**：应用服务，协调领域服务和外部服务
- **接口层**：控制器，处理HTTP请求
- **基础设施层**：数据库访问、缓存、外部服务调用等

## 3. 领域服务

### 3.1 服务器端领域服务

- **IEcoMarketServerSecretDomainService**：管理客户端密钥，负责注册和验证
- **IEcoMarketServerConfigDomainService**：管理服务器端配置，处理配置的保存、发布、下线和审批
- **IEcoMarketServerPublishConfigDomainService**：管理已发布配置，提供查询和管理功能

### 3.2 客户端领域服务

- **IEcoMarketClientSecretService**：管理客户端密钥，与服务器进行通信
- **EcoMarketServerApiService**：封装对服务器API的调用，处理HTTP请求和响应

## 4. 控制器层

控制器层应保持简洁，主要职责是：
1. 接收HTTP请求
2. 参数校验
3. 调用领域服务
4. 返回响应

不应在控制器中包含业务逻辑，业务逻辑应放在领域服务中。

### 4.1 服务器端控制器

- **EcoMarketServerSecretController**：处理客户端注册和验证
- **EcoMarketServerConfigController**：处理配置的保存、发布、下线和审批
- **EcoMarketServerPublishConfigController**：处理已发布配置的查询

### 4.2 客户端控制器

- **EcoMarketClientConfigController**：处理客户端配置的保存、发布、下线等操作

## 5. 鉴权机制

客户端与服务器之间的通信采用客户端ID和密钥进行鉴权：

1. 客户端首次使用时，通过服务器端注册接口获取客户端ID和密钥
2. 后续所有请求都需要携带客户端ID和密钥
3. 服务器端验证客户端ID和密钥的有效性

## 6. 配置管理

### 6.1 配置类

- **EcoMarketProperties**：解析YAML配置
- **EcoMarketConfig**：提供配置相关的Bean
- **EcoMarketCondition**：根据模式类型条件化启用不同的服务
- **EcoMarketConfigUtils**：提供静态方法访问配置
- **ConditionalOnEcoMarketMode**：条件化配置注解

### 6.2 配置示例

```yaml
ecoMarket:
  mode:
    type: client  # 或 server
  server:
    baseUrl: http://eco-market-server:8080
```

## 7. 枚举类型

### 7.1 分享状态

```java
public enum EcoMarketShareStatusEnum {
    DRAFT(1, "草稿"),
    REVIEWING(2, "审核中"),
    PUBLISHED(3, "已发布"),
    OFFLINE(4, "已下线"),
    REJECTED(5, "驳回");
}
```

### 7.2 使用状态

```java
public enum EcoMarketUseStatusEnum {
    ENABLED(1, "启用"),
    DISABLED(2, "禁用");
}
```

### 7.3 拥有标记

```java
public enum EcoMarketOwnedFlagEnum {
    NO(0, "否"),
    YES(1, "是");
}
```

## 8. 数据库表

主要数据库表包括：
- eco_market_client_secret：客户端密钥表
- eco_market_client_config：客户端配置表
- eco_market_client_publish_config：客户端已发布配置表
- eco_market_server_secret：服务器端密钥表
- eco_market_server_config：服务器端配置表
- eco_market_server_publish_config：服务器端已发布配置表

详细表结构参见 eco_market.sql 文件。 