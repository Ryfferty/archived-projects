package com.xspaceagi.compose.infra.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.compose.infra.dao.entity.CustomFieldDefinition;

import java.util.List;

/**
 * 数据表的字段定义
 */
public interface CustomFieldDefinitionService extends IService<CustomFieldDefinition> {

    /**
     * 根据ID集合查询列表
     *
     * @param ids id集合
     * @return 列表
     */
    List<CustomFieldDefinition> queryListByIds(List<Long> ids);

    /**
     * 根据主键查询 单条记录
     *
     * @param id id
     * @return 单条记录
     */
    CustomFieldDefinition queryOneInfoById(Long id);

    /**
     * 根据表id查询记录数
     *
     * @param tableId 表id
     * @return 记录数
     */
    Long queryCountByTableId(Long tableId);

    /**
     * 更新
     *
     * @param entity entity
     * @return id
     */
    Long updateInfo(CustomFieldDefinition entity);

    /**
     * 新增
     */
    Long addInfo(CustomFieldDefinition entity);

    /**
     * 批量新增
     * 
     * @param entityList  新增列表
     * @param userContext 用户上下文
     */
    void batchAddInfo(List<CustomFieldDefinition> entityList);

    /**
     * 批量更新
     * 
     * @param entityList  更新列表
     * @param userContext 用户上下文
     */
    void batchUpdateInfo(List<CustomFieldDefinition> entityList);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 根据表id删除
     *
     * @param tableId 表id
     */
    void deleteByTableId(Long tableId);

    /**
     * 根据表id查询列表
     *
     * @param tableId 表id
     * @return 列表
     */
    List<CustomFieldDefinition> queryListByTableId(Long tableId);

    /**
     * 根据表id集合查询列表
     * 
     * @param tableIds 表id集合
     * @return 列表
     */
    List<CustomFieldDefinition> queryListByTableIds(List<Long> tableIds);

}
