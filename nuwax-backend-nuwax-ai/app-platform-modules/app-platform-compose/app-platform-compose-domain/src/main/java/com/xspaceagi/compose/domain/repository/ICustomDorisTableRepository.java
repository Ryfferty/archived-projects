package com.xspaceagi.compose.domain.repository;

import com.xspaceagi.compose.sdk.vo.data.ExecuteRawResultVo;
import com.xspaceagi.compose.sdk.vo.doris.DorisTableDefinitionVo;

import java.util.List;
import java.util.Map;

/**
 * doris 数据库操作, repository层
 */
public interface ICustomDorisTableRepository {

        /**
         * 检查表是否存在数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @return 是否存在数据
         */
        boolean hasData(String database, String table);

        /**
         * 检查表是否存在
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @return 是否存在
         */
        boolean tableExists(String database, String table);

        /**
         * 执行建表SQL
         * 
         * @param createTableSql 建表SQL
         */
        void executeCreateTable(String createTableSql);

        /**
         * 创建唯一索引
         * 
         * @param database  Doris数据库名
         * @param table     Doris表名
         * @param fieldName 字段名
         */
        void createUniqueIndex(String database, String table, String fieldName);

        /**
         * 删除表
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         */
        void dropTable(String database, String table);

        /**
         * 清空表数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         */
        void truncateTable(String database, String table);

        /**
         * 检查字段值是否唯一
         * 
         * @param database   Doris数据库名
         * @param table      Doris表名
         * @param fieldName  字段名
         * @param fieldValue 字段值
         * @return 是否唯一
         */
        boolean isFieldValueUnique(String database, String table, String fieldName, String fieldValue);

        /**
         * 执行表结构变更SQL
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @param alterSql ALTER TABLE SQL语句
         */
        void executeAlterTable(String database, String table, String alterSql);

        /**
         * 删除表数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @param id       主键ID
         * @return 影响的行数
         */
        int deleteTableDataById(String database, String table, Long id);

        /**
         * 获取表定义信息
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @return 表定义信息
         */
        DorisTableDefinitionVo getTableDefinition(String database, String table);

        /**
         * 插入单行数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @param data     数据Map，key为字段名，value为字段值
         * @return 影响的行数
         */
        int insertRow(String database, String table, Map<String, Object> data);

        /**
         * 批量插入数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @param dataList 数据列表，每个Map代表一行数据
         * @return 影响的行数
         */
        int batchInsert(String database, String table, List<Map<String, Object>> dataList);

        /**
         * 更新单行数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @param id       主键ID
         * @param data     要更新的数据，key为字段名，value为字段值
         * @return 影响的行数
         */
        int updateRow(String database, String table, Long id, Map<String, Object> data);

        /**
         * 批量更新数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @param dataList 数据列表，每个Map必须包含id字段
         * @return 影响的行数
         */
        int batchUpdate(String database, String table, List<Map<String, Object>> dataList);

        /**
         * 根据条件查询数据
         * 
         * @param database   Doris数据库名
         * @param table      Doris表名
         * @param conditions 查询条件，key为字段名，value为字段值
         * @param orderBy    排序字段 (可选)，格式：字段名 ASC/DESC。为空或null则不排序。
         * @param offset     偏移量
         * @param limit      限制条数
         * @return 查询结果列表
         */
        List<Map<String, Object>> queryByConditions(String database, String table,
                        Map<String, Object> conditions, String orderBy, Integer offset, Integer limit);

        /**
         * 根据条件统计数据
         * 
         * @param database   Doris数据库名
         * @param table      Doris表名
         * @param conditions 查询条件，key为字段名，value为字段值
         * @return 统计结果
         */
        long countByConditions(String database, String table, Map<String, Object> conditions);

        /**
         * 根据条件删除数据
         * 
         * @param database   Doris数据库名
         * @param table      Doris表名
         * @param conditions 删除条件，key为字段名，value为字段值
         * @return 影响的行数
         */
        int deleteByConditions(String database, String table, Map<String, Object> conditions);

        /**
         * 批量删除数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @param ids      ID列表
         * @return 影响的行数
         */
        int batchDelete(String database, String table, List<Long> ids);

        /**
         * 查询所有数据（带限制条数）
         * 
         * @param database   Doris数据库名
         * @param table      Doris表名
         * @param conditions 查询条件，key为字段名，value为字段值
         * @param orderBy    排序字段 (可选)，格式：字段名 ASC/DESC。为空或null则不排序。
         * @param limit      限制条数 (为 null 或 < 1 时不限制，由实现层控制最大值)
         * @return 查询结果列表
         */
        List<Map<String, Object>> queryAllData(String database, String table,
                        Map<String, Object> conditions, String orderBy, Integer limit);

        /**
         * 执行用户提供的原始只读SQL查询
         * 
         * @param sql    完整的 SQL 查询语句
         * @param params SQL 语句中的参数 (按 '?' 顺序传入)
         * @return 查询结果列表
         */
        ExecuteRawResultVo executeRawQuery(String sql, Object... params);

        /**
         * 查询用户的sql语句,返回总条数,sql一定是总条数
         * @param sql
         * @return
         */
        Long countRawQuery(String sql);

        /**
         * 获取表的建表DDL语句
         *
         * @param database Doris数据库名
         * @param table    Doris表名
         * @return 建表DDL语句
         */
        String getCreateTableDdl(String database, String table);

        /**
         * 执行原始的管理/DML/DDL SQL语句 (高风险操作，请谨慎使用!)
         * <p>
         * 注意: 主要用于 DML (INSERT, UPDATE, DELETE) 和 DDL (CREATE, ALTER, DROP) 语句。
         * 对于 DML，返回受影响的行数。
         * 对于 DDL，通常返回 0。
         * 不应用于 SELECT 语句 (会抛出异常)。
         * </p>
         *
         * @param sql 待执行的SQL语句
         * @return 受影响的行数 (对于DML语句) 或 0 (对于DDL语句)
         */
        int executeRawAdminSql(String sql);

        /**
         * 批量执行原始的管理/DML/DDL SQL语句 (高风险操作，请谨慎使用!)
         * 
         * @param sqls 待执行的SQL语句列表
         */
        void executeRawAdminSqls(List<String> sqls);

        /**
         * 批量执行SQL的策略枚举
         */
        enum BatchExecuteStrategy {
            FAIL_FAST,      // 遇到错误立即失败并回滚（原有行为）
            CONTINUE_ON_ERROR, // 遇到错误继续执行其他SQL
            ATOMIC_BATCH    // 原子性批处理，要么全成功要么全失败
        }

        /**
         * 根据不同策略批量执行SQL语句 (高风险操作，请谨慎使用!)
         * 
         * @param sqls SQL语句列表
         * @param strategy 执行策略
         */
        void executeRawAdminSqlsWithStrategy(List<String> sqls, BatchExecuteStrategy strategy);

        /**
         * 策略1：遇到错误立即失败（原有行为）
         * 
         * @param sqls SQL语句列表
         */
        void executeFailFast(List<String> sqls);

        /**
         * 策略2：遇到错误继续执行其他SQL（无事务保护）
         * 
         * @param sqls SQL语句列表
         */
        void executeContinueOnError(List<String> sqls);

        /**
         * 策略3：原子性批处理，逐个执行但保持事务完整性
         * 
         * @param sqls SQL语句列表
         */
        void executeAtomicBatch(List<String> sqls);
        /**
         * 查询单个表的数据总条数
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @return 表的总条数
         */
        Long getTableTotal(String database, String table);

        /**
         * 根据主键id,查询对应数据是否存在
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @param id       主键id
         */
        boolean queryDorisTableRowDataById(String database, String table, Long id);

        /**
         * 清空业务数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         */
        void clearBusinessData(String database, String table);


        /**
         * 查询是否存在业务数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @return
         */
        boolean existTableData(String database, String table);

        /**
         * 执行原始的DDL SQL语句 (高风险操作，请谨慎使用!)
         * 
         * @param sql 待执行的DDL SQL语句
         */
        void executeRawDDL(String sql);
}
