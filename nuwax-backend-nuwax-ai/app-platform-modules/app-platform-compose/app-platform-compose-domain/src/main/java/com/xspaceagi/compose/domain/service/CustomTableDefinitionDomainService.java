package com.xspaceagi.compose.domain.service;

import java.io.OutputStream;
import java.util.List;

import com.xspaceagi.compose.domain.dto.CustomEmptyTableVo;
import com.xspaceagi.compose.domain.dto.FieldCreationResult;
import com.xspaceagi.compose.domain.model.CustomFieldDefinitionModel;
import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import com.xspaceagi.compose.sdk.request.DorisToolTableDefineRequest;
import com.xspaceagi.compose.sdk.request.QueryDorisTableDefinePageRequest;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.page.SuperPage;

/**
 * 表定义
 */
public interface CustomTableDefinitionDomainService {

    /**
     * 新增空表定义
     * 
     * @param model
     * @param userContext
     * @return
     */
    Long addInfo(CustomEmptyTableVo model, UserContext userContext);

    /**
     * 更新表定义
     * 
     * @param model
     * @param userContext
     */
    void updateInfo(CustomTableDefinitionModel model, List<CustomFieldDefinitionModel> fieldList, UserContext userContext);

    /**
     * 事物更新
     * @param model
     * @param fieldList
     * @param userContext
     */
    void updateTableDefinitionInTransaction(CustomTableDefinitionModel model,
                                            List<CustomFieldDefinitionModel> fieldList, UserContext userContext);
    /**
     * 更新表名称
     * 
     * @param model
     * @param userContext
     */
    void updateTableName(CustomTableDefinitionModel model, UserContext userContext);

    /**
     * 删除表定义
     * 
     * @param tableId
     * @param userContext
     */
    CustomTableDefinitionModel deleteById(Long tableId, UserContext userContext);

    /**
     * 查询表定义信息
     * 
     * @param tableId 表定义ID
     * @return
     */
    CustomTableDefinitionModel queryOneTableInfoById(Long tableId);

    /**
     * 查询所有表定义信息,根据筛选条件查询
     * 
     * @param request 筛选条件
     * @return
     */
    List<CustomTableDefinitionModel> queryAllTableDefineList(DorisToolTableDefineRequest request);

    /**
     * 根据空间ID查询表定义信息
     * 
     * @param spaceId 空间ID
     * @return 表定义信息列表
     */
    List<CustomTableDefinitionModel> queryTableDefineBySpaceId(Long spaceId);
    /**
     * 导出Doris表数据到Excel
     * 
     * @param tableId      表定义ID
     * @param outputStream 输出流，用于写入Excel数据
     * @param userContext  用户上下文
     */
    void exportTableDataToExcel(Long tableId, OutputStream outputStream, UserContext userContext);

    /**
     * 获取表的原始建表DDL语句
     * 如果表不存在,则返回null
     *
     * @param tableId 表ID
     * @param tableDefine 表定义
     * @return 建表DDL语句
     */
    String getCreateTableDdl(Long tableId, CustomTableDefinitionModel tableDefine);

    /**
     * 根据空间ID查询表定义信息
     * 
     * @param request 查询请求
     * @return 表定义信息列表
     */
    SuperPage<CustomTableDefinitionModel> queryPageTableDefine(QueryDorisTableDefinePageRequest request);

    /**
     * 复制表结构定义
     * 
     * @param tableId
     * @param userContext
     * @return
     */
    Long copyTableDefinition(Long tableId, UserContext userContext);

    /**
     * 修改表定义的最后更新时间
     * 
     * @param tableId
     * @param userContext
     */
    void updateTableLastModified(Long tableId, UserContext userContext);

    /**
     * 根据Excel表头创建新字段
     *3
     * @param tableModel       当前表模型
     * @param newFieldsToCreate 要创建的新字段的中文名列表
     * @param userContext      用户上下文
     * @return 包含更新后表模型和字段映射的结果
     */
    FieldCreationResult handleNewFieldsFromExcel(CustomTableDefinitionModel tableModel, List<String> newFieldsToCreate,
            UserContext userContext);

    /**
     * 统计数据表总数
     *
     * @return 数据表总数
     */
    Long countTotalTables();
}
