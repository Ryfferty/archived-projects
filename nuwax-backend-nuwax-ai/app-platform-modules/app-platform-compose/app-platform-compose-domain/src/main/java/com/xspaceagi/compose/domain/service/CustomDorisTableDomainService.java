package com.xspaceagi.compose.domain.service;

import com.xspaceagi.compose.domain.dto.ColumnDefinitionResult;
import com.xspaceagi.compose.domain.dto.CustomAddBusinessRowDataVo;
import com.xspaceagi.compose.domain.dto.CustomDeleteBusinessRowDataVo;
import com.xspaceagi.compose.domain.dto.CustomUpdateBusinessRowDataVo;
import com.xspaceagi.compose.domain.model.CustomDorisDataRequest;
import com.xspaceagi.compose.domain.model.CustomFieldDefinitionModel;
import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import com.xspaceagi.compose.sdk.DorisDataPage;
import com.xspaceagi.compose.sdk.request.DorisTableDataRequest;
import com.xspaceagi.compose.sdk.vo.data.ExecuteRawResultVo;
import com.xspaceagi.compose.sdk.vo.doris.DorisTableDefinitionVo;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;
import java.util.Map;

/**
 * doris 数据库服务接口
 */
public interface CustomDorisTableDomainService {

        /**
         * 查询doris表数据,分页查询
         * 
         * @param request
         * @return
         */
        DorisDataPage<List<Object>> queryPageDorisTableData(DorisTableDataRequest request,
                        ColumnDefinitionResult columnDefResult);

        /**
         * Web端分页查询Doris表数据
         * 
         * @param request 查询请求参数
         * @return 分页数据结果
         */
        DorisDataPage<Map<String, Object>> queryPageDorisTableDataForWeb(CustomDorisDataRequest request,
                        CustomTableDefinitionModel tableModel);

        /**
         * 删除doris表数据,根据id删除
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @param id       主键id
         */
        void deleteDorisTableRowDataById(String database, String table, Long id);

        /**
         * 检查Doris表是否存在数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @return 是否存在数据
         */
        boolean hasData(String database, String table);

        /**
         * 创建Doris表
         * 
         * @param tableModel 表定义
         */
        void createTable(CustomTableDefinitionModel tableModel);

        /**
         * 删除Doris表
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         */
        void dropTable(String database, String table);

        /**
         * 重建Doris表
         * 
         * @param tableModel 表定义
         */
        void rebuildTable(CustomTableDefinitionModel tableModel);

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
         * 执行Doris表结构变更SQL
         * 
         * @param database           Doris数据库名
         * @param table              Doris表名
         * @param alterSqlStatements ALTER TABLE SQL语句列表
         */
        void alterTable(String database, String table, List<String> alterSqlStatements);

        /**
         * 分页查询表数据 (更通用的接口)
         * 
         * @param database   Doris数据库名
         * @param table      Doris表名
         * @param conditions 查询条件，key为字段名，value为字段值
         * @param orderBy    排序字段，格式：字段名 ASC/DESC
         * @param pageNo     页码 (从1开始)
         * @param pageSize   页大小
         * @return 分页结果 DorisDataPage<Map<String, Object>>
         */
        DorisDataPage<Map<String, Object>> queryPageTableData(String database, String table,
                        Map<String, Object> conditions, String orderBy, int pageNo, int pageSize);

        /**
         * 查询所有数据（带限制条数，更通用的接口）
         * 
         * @param database   Doris数据库名
         * @param table      Doris表名
         * @param conditions 查询条件，key为字段名，value为字段值
         * @param orderBy    排序字段，格式：字段名 ASC/DESC
         * @param limit      限制条数 (为 null 或 < 1 时不限制，由实现层控制最大值)
         * @return 查询结果列表 List<Map<String, Object>>
         */
        List<Map<String, Object>> queryAllTableData(String database, String table,
                        Map<String, Object> conditions, String orderBy, Integer limit);

        /**
         * 生成唯一索引sql
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @param fields   字段列表
         */
        void createUniqueIndexes(String database, String table, List<CustomFieldDefinitionModel> fields);

        /**
         * 在指定数据库中执行原始只读SQL查询 (仅限 SELECT)
         * 
         * @param database 要查询的数据库名称
         * @param sql      完整的 SELECT 查询语句
         * @return 查询结果列表
         */
        ExecuteRawResultVo executeRawQuery(String database, String sql);

        /**
         * 检查表是否存在
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @return 表是否存在
         */
        boolean tableExists(String database, String table);

        /**
         * 清空表数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         */
        void truncateTable(String database, String table);

        /**
         * 获取表定义信息
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @return 表定义信息
         */
        DorisTableDefinitionVo getTableDefinition(String database, String table);

        /**
         * 更新表结构定义。
         * 根据传入的新字段列表与现有字段列表进行比较，生成并执行 ALTER TABLE 语句。
         * 目前支持的操作：添加新字段、修改字段注释。
         *
         * @param tableId   要更新的表的ID
         * @param newFields 最新的字段定义列表
         */
        void updateTableStructure(Long tableId, List<CustomFieldDefinitionModel> newFields,
                        CustomTableDefinitionModel tableModel);

        /**
         * 根据请求参数生成执行sql
         * 
         * @param request
         * @return
         */
        String generateExecuteSql(DorisTableDataRequest request, CustomTableDefinitionModel tableModel);

        /**
         * 新增业务数据
         * 
         * @param request
         * @param tableModel
         */
        void addBusinessData(CustomAddBusinessRowDataVo request, CustomTableDefinitionModel tableModel,
                        UserContext userContext);

        /**
         * 修改业务数据
         * 
         * @param request
         * @param tableModel
         */
        void updateBusinessData(CustomUpdateBusinessRowDataVo request, CustomTableDefinitionModel tableModel,
                        UserContext userContext);

        /**
         * 删除业务数据
         * 
         * @param request
         * @param tableModel
         */
        void deleteBusinessData(CustomDeleteBusinessRowDataVo request, CustomTableDefinitionModel tableModel,
                        UserContext userContext);

        /**
         * 获取表的总条数
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
         * @param tableId
         * @param userContext
         */
        void clearBusinessData(Long tableId, CustomTableDefinitionModel tableModel, UserContext userContext);

        /**
         * 查询是否存在业务数据
         * 
         * @param database Doris数据库名
         * @param table    Doris表名
         * @return
         */
        boolean existTableData(String database, String table);

        /**
         * 批量插入数据
         * 
         * @param tableDefinitionModel 表定义
         * @param excelData            数据
         * @param userContext          用户上下文
         */
        void batchInsertData(CustomTableDefinitionModel tableDefinitionModel, List<Map<String, Object>> excelData,
                        UserContext userContext);
}
