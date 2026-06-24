-- 用户表索引优化
-- 用于提升用户统计查询性能

-- 1. created 字段索引（核心索引）
-- 优化所有日期范围查询和 GROUP BY 操作
CREATE INDEX idx_user_created ON user(created);

-- 2. 复合索引 (_tenant_id, created)
-- 如果需要按租户隔离统计，这个索引更高效
-- 适用于多租户场景下的统计查询
CREATE INDEX idx_user_tenant_created ON user(_tenant_id, created);

-- 查看现有索引
-- SHOW INDEX FROM user;

-- 查看索引使用情况
-- EXPLAIN SELECT COUNT(*) FROM user WHERE created >= CURDATE() - INTERVAL 7 DAY;