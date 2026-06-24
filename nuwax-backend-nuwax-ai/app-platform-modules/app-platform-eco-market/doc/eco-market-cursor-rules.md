# 生态市场模块开发规则

## 模块概述

生态市场模块是一个用于管理和分享插件、模板和MCP的平台，支持服务器端和客户端两种模式。服务器端负责集中管理配置和审核，客户端负责使用和提交配置。

## 架构设计

### 模式选择

生态市场支持两种模式：
- **服务器模式**：作为中心节点，管理所有配置和审核流程
- **客户端模式**：作为使用节点，从服务器获取配置并提交配置审核

通过配置文件中的 `eco-market.mode.type` 属性设置，可选值为 `server` 或 `client`。

### 分层结构

遵循DDD设计原则，模块分为以下几层：
- **领域层**：核心业务逻辑，包括领域模型和领域服务
- **应用层**：应用服务，协调领域服务和外部服务
- **接口层**：控制器，处理HTTP请求
- **基础设施层**：数据库访问、缓存、外部服务调用等

## 领域服务

### 服务器端领域服务

- **IEcoMarketServerSecretDomainService**：管理客户端密钥，负责注册和验证
- **IEcoMarketServerConfigDomainService**：管理服务器端配置，处理配置的保存、发布、下线和审批
- **IEcoMarketServerPublishConfigDomainService**：管理已发布配置，提供查询和管理功能

### 客户端领域服务

- **IEcoMarketClientSecretService**：管理客户端密钥，与服务器进行通信
- **EcoMarketServerApiService**：封装对服务器API的调用，处理HTTP请求和响应

## 控制器层

控制器层应保持简洁，主要职责是：
1. 接收HTTP请求
2. 参数校验
3. 调用领域服务
4. 返回响应

不应在控制器中包含业务逻辑，业务逻辑应放在领域服务中。

## 鉴权机制

客户端与服务器之间的通信采用客户端ID和密钥进行鉴权：

1. 客户端首次使用时，通过服务器端注册接口获取客户端ID和密钥
2. 后续所有请求都需要携带客户端ID和密钥
3. 服务器端验证客户端ID和密钥的有效性

## 配置管理

### 配置类

- **EcoMarketProperties**：解析YAML配置
- **EcoMarketConfig**：提供配置相关的Bean
- **EcoMarketCondition**：根据模式类型条件化启用不同的服务
- **EcoMarketConfigUtils**：提供静态方法访问配置
- **ConditionalOnEcoMarketMode**：条件化配置注解

## 枚举类型

主要枚举类型包括：
- **EcoMarketShareStatusEnum**：分享状态（草稿、审核中、已发布、已下线、驳回）
- **EcoMarketUseStatusEnum**：使用状态（启用、禁用）
- **EcoMarketOwnedFlagEnum**：拥有标记（是、否）

## 开发规范

1. 控制器只负责请求处理，不包含业务逻辑
2. 业务逻辑应放在领域服务中
3. 应用服务用于协调不同的领域服务
4. 使用枚举类型表示状态和标记
5. 客户端与服务器通信必须使用客户端ID和密钥鉴权 