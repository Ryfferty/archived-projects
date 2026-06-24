package com.xspaceagi.knowledge.core.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author soddy
 * @description 针对表【knowledge_config(知识库表)】的数据库操作Mapper
 * @createDate 2025-01-21 15:33:38
 * @Entity com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeConfig
 */
public interface KnowledgeConfigMapper extends BaseMapper<KnowledgeConfig> {

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
    List<KnowledgeConfig> queryList(@Param("queryMap") Map<String, Object> queryMap,
            @Param("orderColumns") List<OrderItem> orderColumns,
            @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);

    /**
     * 查询所有知识库ID（用于全文检索数据迁移）
     *
     * @return 知识库ID列表
     */
    List<Long> queryAllKbIds();

    /**
     * 查询未同步的知识库列表
     *
     * @param tenantId 租户ID
     * @return 未同步的知识库列表
     */
    List<KnowledgeConfig> queryUnsyncedKnowledgeBases(@Param("tenantId") Long tenantId);

    /**
     * 更新知识库全文检索同步状态
     *
     * @param kbId 知识库ID
     * @param status 同步状态
     * @param segmentCount 分段数量
     * @param modified 修改时间(用于保持原值)
     */
    void updateFulltextSyncStatus(@Param("kbId") Long kbId, 
                                   @Param("status") Integer status,
                                  @Param("segmentCount") Long segmentCount,
                                  @Param("modified") java.time.LocalDateTime modified);

    /**
     * 更新知识库全文检索同步状态（不包含分段数量）
     *
     * @param kbId 知识库ID
     * @param status 同步状态
     * @param modified 修改时间(用于保持原值)
     */
    void updateFulltextSyncStatusWithoutSegmentCount(@Param("kbId") Long kbId,
                                                     @Param("status") Integer status,
                                                     @Param("modified") java.time.LocalDateTime modified);

    /**
     * 查询同步状态统计
     *
     * @param tenantId 租户ID
     * @return 同步状态统计 Map<状态, 数量>
     */
    Map<Integer, Long> querySyncStatusStats(@Param("tenantId") Long tenantId);

}
