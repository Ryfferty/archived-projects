package com.xspaceagi.knowledge.core.infra.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeRawSegment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author soddy
 * @description 针对表【knowledge_raw_segment(原始分段（也称chunk）表，这些信息待生成问答后可以不再保存)】的数据库操作Mapper
 * @createDate 2025-01-21 15:33:38
 * @Entity com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeRawSegment
 */
public interface KnowledgeRawSegmentMapper extends BaseMapper<KnowledgeRawSegment> {

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
    List<KnowledgeRawSegment> queryList(@Param("queryMap") Map<String, Object> queryMap,
            @Param("orderColumns") List<OrderItem> orderColumns,
            @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);

    /**
     * 查询待生成问答的分段信息,根据字段:qaStatus=-1,created在days天前
     * 
     * @param days       天数
     * @param startIndex
     * @param endIndex
     * @return
     */
    List<KnowledgeRawSegment> queryListForPendingQa(@Param("days") Integer days,
            @Param("startIndex") Long startIndex,
            @Param("endIndex") Long endIndex);

    // ========== 全文检索相关方法 ========== //

    /**
     * 分页查询知识库的所有分段（用于全文检索数据迁移）
     *
     * @param kbId 知识库ID
     * @param startIndex 开始索引
     * @param endIndex 结束索引
     * @return 分段列表
     */
    List<KnowledgeRawSegment> queryByKbIdWithPage(@Param("kbId") Long kbId,
            @Param("startIndex") Long startIndex,
            @Param("endIndex") Long endIndex);

    /**
     * 查询知识库的所有分段ID（用于全文检索数据一致性检查）
     *
     * @param kbId 知识库ID
     * @return 分段ID列表
     */
    List<Long> queryAllSegmentIdsByKbId(@Param("kbId") Long kbId);

    /**
     * 统计知识库的分段数量
     *
     * @param kbId 知识库ID
     * @return 分段数量
     */
    Long countByKbId(@Param("kbId") Long kbId);

    /**
     * 查询未同步的分段列表（分页）
     *
     * @param kbId 知识库ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 未同步的分段列表
     */
    List<KnowledgeRawSegment> queryUnsyncedSegments(@Param("kbId") Long kbId,
                                                     @Param("offset") Integer offset,
                                                     @Param("limit") Integer limit);

    /**
     * 统计未同步的分段数量
     *
     * @param kbId 知识库ID
     * @return 未同步分段数量
     */
    Long countUnsyncedByKbId(@Param("kbId") Long kbId);

    /**
     * 批量更新分段同步状态
     *
     * @param segmentIds 分段ID列表
     * @param status 同步状态
     */
    void batchUpdateSyncStatus(@Param("segmentIds") List<Long> segmentIds,
                                @Param("status") Integer status);

    /**
     * 查询知识库已同步的分段数量
     *
     * @param kbId 知识库ID
     * @return 已同步分段数量
     */
    Long countSyncedByKbId(@Param("kbId") Long kbId);

}
