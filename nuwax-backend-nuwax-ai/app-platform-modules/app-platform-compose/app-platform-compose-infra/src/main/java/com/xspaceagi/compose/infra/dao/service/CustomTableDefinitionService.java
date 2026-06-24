package com.xspaceagi.compose.infra.dao.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.compose.infra.dao.entity.CustomFieldDefinition;
import com.xspaceagi.compose.infra.dao.entity.CustomTableDefinition;

import java.util.List;
import java.util.Map;

/**
 * 自定义数据表定义服务
 */
public interface CustomTableDefinitionService extends IService<CustomTableDefinition> {

    /**
     * 根据ID集合查询列表
     *
     * @param ids id集合
     * @return 列表
     */
    List<CustomTableDefinition> queryListByIds(List<Long> ids);

    /**
     * 根据主键查询 单条记录
     *
     * @param id id
     * @return 单条记录
     */
    CustomTableDefinition queryOneInfoById(Long id);

    /**
     * 更新
     *
     * @param entity entity
     * @return id
     */
    Long updateInfo(CustomTableDefinition entity);

    /**
     * 新增
     */
    Long addInfo(CustomTableDefinition entity);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 分页查询
     *
     * @param queryMap     查询条件
     * @param orderColumns 排序条件
     * @param startIndex   开始索引
     * @param pageSize     页大小
     * @return 分页数据
     */
    List<CustomTableDefinition> pageQuery(Map<String, Object> queryMap, List<OrderItem> orderColumns, Long startIndex, Long pageSize);

    /**
     * 查询总数
     *
     * @param queryMap 查询条件
     * @return 总数
     */
    Long queryTotal(Map<String, Object> queryMap);

    /**
     * 根据条件查询表定义列表
     *
     * @param condition 查询条件
     * @return 表定义列表
     */
    List<CustomTableDefinition> queryListByCondition(CustomTableDefinition condition);

    /**
     * 根据空间ID查询表定义列表
     * 
     * @param spaceId 空间ID
     * @return 表定义列表
     */
    List<CustomTableDefinition> queryListBySpaceId(Long spaceId);
}
