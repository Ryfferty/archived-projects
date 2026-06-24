# 记忆管理模块 - 数据字典

## 表1: memory_unit (记忆单元表)

### 表说明
用于存储AI Agent的记忆单元数据，支持多种类型的记忆分类和管理。

### 字段说明

| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| id | BIGINT(20) | 是 | AUTO_INCREMENT | 主键ID，自增 |
| _tenant_id | BIGINT(20) | 是 | - | 租户ID，多租户隔离标识 |
| user_id | BIGINT(20) | 是 | - | 用户ID，记忆所属用户 |
| agent_id | BIGINT(20) | 否 | NULL | 代理ID，关联的AI代理 |
| category | VARCHAR(50) | 是 | - | 一级分类，如：工作、学习、生活等 |
| sub_category | VARCHAR(100) | 否 | NULL | 二级分类，更细粒度的分类 |
| content_json | JSON | 否 | NULL | 记忆内容，JSON格式存储结构化数据 |
| is_sensitive | TINYINT(1) | 是 | 0 | 是否敏感信息：0-否，1-是 |
| status | ENUM | 是 | 'active' | 状态：active-活跃，archived-已归档，deleted-已删除 |
| created | DATETIME | 是 | CURRENT_TIMESTAMP | 创建时间 |
| modified | DATETIME | 是 | CURRENT_TIMESTAMP ON UPDATE | 修改时间 |

### 索引说明

| 索引名 | 字段 | 类型 | 说明 |
|--------|------|------|------|
| PRIMARY | id | PRIMARY | 主键索引 |
| idx_tenant_user | _tenant_id, user_id | INDEX | 租户用户联合索引，支持多租户查询 |
| idx_agent | agent_id | INDEX | 代理ID索引，支持按代理查询 |
| idx_category | category, sub_category | INDEX | 分类联合索引，支持分类查询 |
| idx_status | status | INDEX | 状态索引，支持状态筛选 |
| idx_created | created | INDEX | 创建时间索引，支持时间范围查询 |

### 字段约束

- `status` 字段只能为：'active', 'archived', 'deleted'
- `is_sensitive` 只能为 0 或 1
- `_tenant_id` 和 `user_id` 必须有值，用于数据隔离

### 使用场景

1. **短期记忆**: 临时存储对话上下文
2. **长期记忆**: 持久化存储重要信息
3. **工作记忆**: 当前任务相关的活跃记忆
4. **敏感信息**: 标记需要特殊保护的数据

---

## 表2: memory_unit_tag (记忆单元标签表)

### 表说明
用于为记忆单元添加标签，支持灵活的分类和检索。

### 字段说明

| 字段名 | 类型 | 必填 | 默认值 | 说明 |
|--------|------|------|--------|------|
| id | BIGINT(20) | 是 | AUTO_INCREMENT | 主键ID，自增 |
| _tenant_id | BIGINT(20) | 是 | - | 租户ID，多租户隔离标识 |
| user_id | BIGINT(20) | 是 | - | 用户ID，标签创建者 |
| memory_id | BIGINT(20) | 是 | - | 记忆ID，关联到memory_unit表 |
| tag_name | VARCHAR(100) | 是 | - | 标签名称 |
| created | DATETIME | 是 | CURRENT_TIMESTAMP | 创建时间 |
| modified | DATETIME | 是 | CURRENT_TIMESTAMP ON UPDATE | 修改时间 |

### 索引说明

| 索引名 | 字段 | 类型 | 说明 |
|--------|------|------|------|
| PRIMARY | id | PRIMARY | 主键索引 |
| idx_tenant_user | _tenant_id, user_id | INDEX | 租户用户联合索引 |
| idx_memory | memory_id | INDEX | 记忆ID索引，支持按记忆查询标签 |
| idx_tag | tag_name | INDEX | 标签名称索引，支持按标签查询 |

### 外键约束

| 约束名 | 字段 | 关联表 | 关联字段 | 级联操作 |
|--------|------|--------|----------|----------|
| fk_memory_tag | memory_id | memory_unit | id | ON DELETE CASCADE |

### 级联删除说明
当删除 memory_unit 表中的记录时，会自动删除 memory_unit_tag 表中对应的标签记录。

---

## 表关系

```
memory_unit (1) ──────< (N) memory_unit_tag
    一对多关系
    一个记忆可以有多个标签
```

---

## 业务规则

### 1. 数据隔离
- 所有数据按 `_tenant_id` 和 `user_id` 进行隔离
- 查询时必须包含租户和用户条件

### 2. 状态管理
- 新创建的记忆默认状态为 'active'
- 删除操作采用软删除，将状态设置为 'deleted'
- 过期记忆应归档为 'archived'

### 3. 标签规则
- 一个记忆可以有多个标签
- 标签名称不区分大小写（建议在应用层处理）
- 重复标签由应用层控制

### 4. 敏感信息处理
- 标记为敏感的信息需要特殊处理
- 敏感信息不应在日志中显示完整内容

---

## 性能优化建议

### 1. 分区策略
对于大数据量场景，建议按时间或租户进行分区：
```sql
-- 按创建时间分区（可选）
ALTER TABLE memory_unit PARTITION BY RANGE (TO_DAYS(created)) (
    PARTITION p_old VALUES LESS THAN (TO_DAYS('2026-01-01')),
    PARTITION p_current VALUES LESS THAN (MAXVALUE)
);
```

### 2. 查询优化
- 使用覆盖索引减少回表
- 避免SELECT *，只查询需要的字段
- 合理使用LIMIT分页

### 3. 数据清理
- 定期清理已删除超过30天的数据
- 清理孤立的标签数据
- 定期执行ANALYZE TABLE和OPTIMIZE TABLE

---

## 数据迁移注意事项

1. **字符集**: 使用 utf8mb4 以支持emoji等特殊字符
2. **时区**: 确保应用服务器和数据库时区一致
3. **JSON字段**: MySQL 5.7+ 支持JSON类型，低版本需要使用TEXT
4. **外键**: 如需禁用外键检查（批量导入时）：
   ```sql
   SET FOREIGN_KEY_CHECKS=0;
   -- 执行导入操作
   SET FOREIGN_KEY_CHECKS=1;
   ```
