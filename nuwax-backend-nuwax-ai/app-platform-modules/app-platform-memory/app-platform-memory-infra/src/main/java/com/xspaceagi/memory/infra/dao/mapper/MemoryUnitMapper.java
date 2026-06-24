package com.xspaceagi.memory.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.xspaceagi.memory.infra.dao.entity.MemoryUnit;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 记忆单元Mapper
 *
 * @description 针对表【memory_unit(记忆单元表)】的数据库操作Mapper
 * @createDate 2026-03-06
 * @Entity com.xspaceagi.memory.infra.dao.entity.MemoryUnit
 */
public interface MemoryUnitMapper extends BaseMapper<MemoryUnit> {

    /**
     * 总条数查询
     *
     * @param queryMap 筛选条件
     * @return 总条数
     */
    Long queryTotal(@Param("queryMap") Map<String, Object> queryMap);

    /**
     * 列表查询
     *
     * @param queryMap     筛选条件
     * @param orderColumns 排序
     * @param startIndex   索引开始位置
     * @param pageSize     业务大小
     * @return 列表
     */
    List<MemoryUnit> queryList(@Param("queryMap") Map<String, Object> queryMap,
                               @Param("orderColumns") List<OrderItem> orderColumns,
                               @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);

    /**
     * 根据用户ID和分类查询记忆单元
     *
     * @param userId   用户ID
     * @param category 一级分类
     * @return 记忆单元列表
     */
    List<MemoryUnit> findByUserIdAndCategory(@Param("userId") Long userId, @Param("agentId") Long agentId, @Param("category") String category, @Param("subCategory") String subCategory);

    /**
     * 根据代理ID查询记忆单元
     *
     * @param agentId 代理ID
     * @return 记忆单元列表
     */
    List<MemoryUnit> findByAgentId(@Param("agentId") Long agentId);

    /**
     * 根据分类和JSON字段值查询记忆单元（精确匹配）
     *
     * @param tenantId    租户ID
     * @param userId      用户ID
     * @param category    一级分类
     * @param subCategory 二级分类
     * @param jsonKey     JSON中的键
     * @param jsonValue   JSON中的值
     * @return 记忆单元列表
     */
    List<MemoryUnit> findByCategoryAndJsonKeyValue(
            @Param("tenantId") Long tenantId,
            @Param("userId") Long userId,
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            @Param("jsonKey") String jsonKey,
            @Param("jsonValue") String jsonValue
    );

    /**
     * 根据分类和JSON字段值查询记忆单元（模糊匹配）
     *
     * @param tenantId    租户ID
     * @param userId      用户ID
     * @param category    一级分类
     * @param subCategory 二级分类
     * @param jsonKey     JSON中的键
     * @param jsonValue   JSON中的值
     * @return 记忆单元列表
     */
    List<MemoryUnit> findByCategoryAndJsonKeyValueLike(
            @Param("tenantId") Long tenantId,
            @Param("userId") Long userId,
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            @Param("jsonKey") String jsonKey,
            @Param("jsonValue") String jsonValue
    );

    /**
     * 根据分类和JSON多个字段值查询记忆单元
     *
     * @param tenantId      租户ID
     * @param userId        用户ID
     * @param category      一级分类
     * @param subCategory   二级分类
     * @param jsonKeyValues JSON键值对 Map<key, value>
     * @return 记忆单元列表
     */
    List<MemoryUnit> findByCategoryAndJsonKeyValues(
            @Param("tenantId") Long tenantId,
            @Param("userId") Long userId,
            @Param("category") String category,
            @Param("subCategory") String subCategory,
            @Param("jsonKeyValues") Map<String, String> jsonKeyValues
    );


    /**
     * 根据用户ID、代理ID和标签查询记忆单元
     *
     * @param userId  用户ID
     * @param agentId 代理ID
     * @param tags    标签列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param startIndex 索引开始位置
     * @param pageSize 业务大小
     * @return 记忆单元列表
     */
    List<MemoryUnit> selectWithJoinTags(@Param("userId") Long userId,
                                        @Param("agentId") Long agentId,
                                        @Param("tags") List<String> tags,
                                        @Param("startTime") Date startTime,
                                        @Param("endTime") Date endTime,
                                        @Param("startIndex") Long startIndex,
                                        @Param("pageSize") Long pageSize);
}
