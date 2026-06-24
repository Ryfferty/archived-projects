-- 数据库架构差异SQL
-- 生成时间: 2026-05-08 10:33:10 UTC

-- 新增表: file_record
CREATE TABLE `file_record` (  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `_tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `target_type` VARCHAR(50) COMMENT '来源对象类型（如：agent、knowledge、message等）',
  `target_id` BIGINT COMMENT '来源对象ID',
  `file_name` VARCHAR(255) NOT NULL COMMENT '文件名称',
  `file_size` BIGINT NOT NULL COMMENT '文件大小（字节）',
  `file_type` VARCHAR(100) COMMENT '文件类型（MIME类型）',
  `file_extension` VARCHAR(20) COMMENT '文件扩展名',
  `metadata` TEXT COMMENT '文件元数据（JSON格式，存储图片宽高、视频时长等）',
  `file_key` VARCHAR(500) NOT NULL COMMENT '文件存储标识key',
  `storage_type` VARCHAR(20) NOT NULL DEFAULT 'file' COMMENT '存储方式（cos:腾讯云COS, oss:阿里云OSS, s3:S3协议, local:本地存储）',
  `file_url` VARCHAR(1000) COMMENT '文件访问URL',
  `auth_required` TINYINT NOT NULL DEFAULT 1 COMMENT '是否需要认证',
  `status` VARCHAR(20) NOT NULL DEFAULT 'active' COMMENT '文件状态（active:正常, deleted:已删除）',
  `created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_file_key` (`file_key`),
  KEY `idx_created` (`created`),
  KEY `idx_storage_type` (`storage_type`),
  KEY `idx_target` (`target_type`, `target_id`),
  KEY `idx_tenant_user` (`_tenant_id`, `user_id`)
);

-- 修改表: publish_apply
ALTER TABLE `publish_apply` ADD COLUMN `ext` JSON COMMENT '扩展字段';

-- 修改表: custom_page_config
ALTER TABLE `custom_page_config` ADD COLUMN `sandbox_id` BIGINT COMMENT '沙盒ID';

-- 修改表: model_config
ALTER TABLE `model_config` ADD COLUMN `pid` VARCHAR(64) NOT NULL DEFAULT 'custom' COMMENT '提供商ID';
ALTER TABLE `model_config` ADD COLUMN `types` JSON COMMENT '模型能力类型列表（Text/Image/Audio/Video/TextEmbedding/MultiEmbedding/Reasoning）';

-- 修改表: custom_page_conversation
ALTER TABLE `custom_page_conversation` ADD COLUMN `session_id` VARCHAR(64) COMMENT '会话ID';
ALTER TABLE `custom_page_conversation` ADD COLUMN `role` VARCHAR(32) COMMENT '消息发送者角色';
ALTER TABLE `custom_page_conversation` ADD COLUMN `request_id` VARCHAR(64) COMMENT '请求ID';

-- 修改表: published
ALTER TABLE `published` ADD COLUMN `ext` JSON COMMENT '扩展字段';

-- 修改表: sandbox_config
ALTER TABLE `sandbox_config` ADD COLUMN `type` VARCHAR(16) NOT NULL DEFAULT 'Agent' COMMENT '沙箱类型：Agent 智能体沙箱；PageApp 应用开发沙箱';
ALTER TABLE `sandbox_config` ADD COLUMN `bind_info` JSON COMMENT '关系绑定';
ALTER TABLE `sandbox_config` ADD COLUMN `isolation` VARCHAR(16) COMMENT '隔离策略，仅页面开发有效';

-- 修改表: skill_config
ALTER TABLE `skill_config` ADD COLUMN `ext` JSON COMMENT '扩展字段';