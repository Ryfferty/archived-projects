package com.xspaceagi.compose.domain.repository;

import com.xspaceagi.compose.domain.dto.ColumnDefinitionResult;
import com.xspaceagi.compose.domain.dto.CustomEmptyTableVo;
import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.infra.service.IQueryViewService;

import java.util.List;

public interface ICustomTableDefinitionRepository extends IQueryViewService<CustomTableDefinitionModel> {
    /**
     * 根据ID集合查询列表
     *
     * @param ids id集合
     * @return 列表
     */
    List<CustomTableDefinitionModel> queryListByIds(List<Long> ids);

    /**
     * 根据主键查询 单条记录
     *
     * @param id id
     * @return 单条记录
     */
    CustomTableDefinitionModel queryOneInfoById(Long id);

    /**
     * 更新
     *
     * @param entity entity
     * @return id
     */
    Long updateInfo(CustomTableDefinitionModel entity, UserContext userContext);


    /**
     * 更新表名称
     * 
     * @param entity
     * @return
     */
    Long updateTableName(CustomTableDefinitionModel entity, UserContext userContext);


    /**
     * 新增
     */
    Long addInfo(CustomTableDefinitionModel entity);

    /**
     * 新增空表定义
     * 
     * @param vo          空表定义
     * @param userContext 用户上下文
     * @return
     */
    Long addEmptyTableInfo(CustomEmptyTableVo vo, UserContext userContext);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);


    /**
     * 根据条件查询表定义列表
     *
     * @param condition 查询条件
     * @return 表定义列表
     */
    List<CustomTableDefinitionModel> queryListByCondition(CustomTableDefinitionModel condition);

    /**
     * 根据空间ID查询表定义列表
     * 
     * @param spaceId 空间ID
     * @return 表定义列表
     */
    List<CustomTableDefinitionModel> queryListBySpaceId(Long spaceId);

    /**
     * 根据表ID获取列定义
     * @param tableId
     * @return
     */
    public ColumnDefinitionResult getColumnDefinitions(Long tableId);


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
     * 统计数据表总数
     *
     * @return 数据表总数
     */
    Long countTotalTables(Long userId);
}
