package com.xspaceagi.knowledge.core.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeDocument;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author soddy
 * @description 针对表【knowledge_document(知识库-原始文档表)】的数据库操作Mapper
 * @createDate 2025-01-21 15:33:38
 * @Entity com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeDocument
 */
public interface KnowledgeDocumentMapper extends BaseMapper<KnowledgeDocument> {

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
    List<KnowledgeDocument> queryList(@Param("queryMap") Map<String, Object> queryMap,
            @Param("orderColumns") List<OrderItem> orderColumns,
            @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);

    /**
     * 根据qaSegmentId查询文档详情
     * 
     * @param qaSegmentId qaSegmentId
     * @return
     */
    KnowledgeDocument queryOneInfoByQaSegmentId(@Param("qaSegmentId") Long qaSegmentId);


    /**
     * 根据知识库id,查询下面所有的 文件id列表
     *
     * @param kbId               知识库id
     * @param knowledgePubStatus 知识库发布状态,可空,空则不限制
     * @return
     */
    List<Long> queryDocIdsByConfigId(@Param("kbId") Long kbId, @Param("knowledgePubStatus") String knowledgePubStatus);

    /**
     * 查询知识库的文件大小
     * 
     * @param kbId 知识库id
     * @return 文件大小
     */
    Long queryTotalFileSize(@Param("kbId") Long kbId);

}
