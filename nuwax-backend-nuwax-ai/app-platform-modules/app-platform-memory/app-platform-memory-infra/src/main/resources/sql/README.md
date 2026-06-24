# 记忆管理模块 - MySQL表创建脚本使用说明

## 📁 文件说明

### 1. `memory_schema.sql` - 完整版脚本
**包含内容：**
- 完整的表创建语句
- 示例数据插入
- 常用查询示例
- 数据清理和维护脚本
- 性能优化建议

**适用场景：** 开发环境搭建、功能测试、学习参考

### 2. `memory_tables_simple.sql` - 简化版脚本
**包含内容：**
- 纯净的表创建语句
- 必要的索引和约束

**适用场景：** 生产环境部署、快速部署

### 3. `verify_tables.sql` - 验证脚本
**包含内容：**
- 表结构检查
- 索引验证
- 外键约束检查
- 数据完整性检查

**适用场景：** 部署后验证、故障排查

### 4. `DATA_DICTIONARY.md` - 数据字典
**包含内容：**
- 详细的字段说明
- 索引说明
- 业务规则
- 性能优化建议

**适用场景：** 开发参考、文档维护

---

## 🚀 使用步骤

### 步骤1: 选择合适的脚本
```bash
# 开发环境（推荐）
mysql -u root -p < memory_schema.sql

# 生产环境
mysql -u root -p < memory_tables_simple.sql
```

### 步骤2: 验证表创建
```bash
mysql -u root -p your_database < verify_tables.sql
```

### 步骤3: 检查结果
登录MySQL查看：
```sql
USE your_database;
SHOW TABLES LIKE 'memory_unit%';
DESCRIBE memory_unit;
DESCRIBE memory_unit_tag;
```

---

## 📋 表结构概览

### memory_unit (记忆单元表)
存储AI Agent的各种记忆数据，支持分类、标签、状态管理。

**核心字段：**
- `id`: 主键
- `_tenant_id`, `user_id`: 多租户隔离
- `category`, `sub_category`: 分类体系
- `content_json`: JSON格式存储记忆内容
- `status`: 状态管理

### memory_unit_tag (记忆标签表)
为记忆单元添加灵活的标签支持。

**核心字段：**
- `id`: 主键
- `memory_id`: 关联记忆单元（外键）
- `tag_name`: 标签名称

**关系：** 一个记忆可以有多个标签（一对多）

---

## 🔧 高级配置

### 字符集设置
```sql
-- 确保使用 utf8mb4 支持emoji
ALTER DATABASE your_database CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

### 性能优化
```sql
-- 分析表优化查询
ANALYZE TABLE memory_unit;
ANALYZE TABLE memory_unit_tag;

-- 优化表空间
OPTIMIZE TABLE memory_unit;
OPTIMIZE TABLE memory_unit_tag;
```

### 备份恢复
```bash
# 备份
mysqldump -u root -p your_database memory_unit memory_unit_tag > backup.sql

# 恢复
mysql -u root -p your_database < backup.sql
```

---

## 📊 数据示例

### 记忆单元示例
```json
{
  "title": "AI项目开发",
  "description": "负责AI代理平台的开发工作",
  "priority": "高",
  "tags": ["重要", "正在进行"],
  "metadata": {
    "start_date": "2026-01-01",
    "end_date": "2026-06-30"
  }
}
```

### 标签示例
```
重要、正在进行、技术、学习、运动、日常
```

---

## ⚠️ 注意事项

### 1. 外键约束
- `memory_unit_tag.memory_id` 有外键约束指向 `memory_unit.id`
- 删除记忆会自动删除相关标签（CASCADE）

### 2. 级联删除
如需临时禁用外键检查（批量操作时）：
```sql
SET FOREIGN_KEY_CHECKS=0;
-- 执行批量操作
SET FOREIGN_KEY_CHECKS=1;
```

### 3. JSON字段
- MySQL 5.7+ 原生支持JSON类型
- 提供JSON函数：JSON_EXTRACT、JSON_SEARCH等
- 自动验证JSON格式

### 4. 状态管理
- 新建记录默认状态为 'active'
- 删除采用软删除（设置status为'deleted'）
- 定期清理归档数据

---

## 🔍 常用查询

### 查询用户的活跃记忆
```sql
SELECT * FROM memory_unit
WHERE user_id = 1001
AND status = 'active'
ORDER BY created DESC;
```

### 查询记忆及其标签
```sql
SELECT m.*, t.tag_name
FROM memory_unit m
LEFT JOIN memory_unit_tag t ON m.id = t.memory_id
WHERE m.user_id = 1001
AND m.status = 'active';
```

### 按标签查询记忆
```sql
SELECT DISTINCT m.*
FROM memory_unit m
INNER JOIN memory_unit_tag t ON m.id = t.memory_id
WHERE t.tag_name = '重要'
AND m.status = 'active';
```

### 统计用户记忆数量（按分类）
```sql
SELECT category, COUNT(*) as count
FROM memory_unit
WHERE user_id = 1001
AND status = 'active'
GROUP BY category;
```

---

## 🛠️ 故障排查

### 问题1: 外键约束错误
```sql
-- 检查外键状态
SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE CONSTRAINT_SCHEMA = DATABASE()
AND TABLE_NAME = 'memory_unit_tag';
```

### 问题2: 字符编码问题
```sql
-- 检查字符集设置
SHOW VARIABLES LIKE 'character%';
SHOW VARIABLES LIKE 'collation%';
```

### 问题3: JSON格式错误
```sql
-- 验证JSON数据
SELECT id, JSON_VALID(content_json) as is_valid
FROM memory_unit
WHERE content_json IS NOT NULL;
```

---

## 📞 技术支持

如有问题，请参考：
1. `DATA_DICTIONARY.md` - 详细的数据字典
2. `verify_tables.sql` - 表结构验证脚本
3. 项目文档：`README.md`
