-- 模型API代理配置表
CREATE TABLE IF NOT EXISTS `model_api_proxy_config` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `tenant_id` BIGINT(20) NOT NULL COMMENT '租户ID',
    `user_api_key` VARCHAR(255) NOT NULL COMMENT '用户API Key',
    `base_url` VARCHAR(512) NOT NULL COMMENT '后端API URL',
    `backend_api_key` VARCHAR(255) NOT NULL COMMENT '后端API Key',
    `model` VARCHAR(128) DEFAULT NULL COMMENT '模型标识',
    `enabled` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    `description` VARCHAR(512) DEFAULT NULL COMMENT '描述',
    `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_tenant_user_api_key` (`tenant_id`, `user_api_key`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_user_api_key` (`user_api_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模型API代理配置表';
