-- 用户请求表索引优化
-- 用于提升访问统计查询性能

-- 1. created 字段索引（核心索引）
-- 优化所有日期范围查询和 GROUP BY 操作
CREATE INDEX idx_user_request_created ON user_request(created);

-- 2. 复合索引 (tenant_id, created)
-- 如果需要按租户隔离统计，这个索引更高效
-- 适用于多租户场景下的统计查询
CREATE INDEX idx_user_request_tenant_created ON user_request(_tenant_id, created);

-- 3. 复合索引 (created, user_id)
-- 对于 COUNT(DISTINCT user_id) 查询有一定优化效果
-- 注意：这个索引较大，根据实际情况评估是否需要
-- CREATE INDEX idx_user_request_created_user ON user_request(created, user_id);

-- 查看现有索引
-- SHOW INDEX FROM user_request;

-- 查看索引使用情况
-- EXPLAIN SELECT COUNT(DISTINCT user_id) FROM user_request WHERE DATE(created) >= DATE_SUB(CURDATE(), INTERVAL 7 DAY);