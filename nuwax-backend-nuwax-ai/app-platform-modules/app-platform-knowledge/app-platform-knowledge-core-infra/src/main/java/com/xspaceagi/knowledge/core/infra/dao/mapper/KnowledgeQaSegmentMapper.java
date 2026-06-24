package com.xspaceagi.knowledge.core.infra.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeQaSegment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.knowledge.domain.dto.EmbeddingStatusDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author soddy
 * @description 针对表【knowledge_qa_segment(问答表)】的数据库操作Mapper
 * @createDate 2025-01-21 15:33:38
 * @Entity com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeQaSegment
 */
public interface KnowledgeQaSegmentMapper extends BaseMapper<KnowledgeQaSegment> {

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
        List<KnowledgeQaSegment> queryList(@Param("queryMap") Map<String, Object> queryMap,
                        @Param("orderColumns") List<OrderItem> orderColumns,
                        @Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);

        /**
         * 查询文档的嵌入状态
         * 
         * @param docId 文档id
         * @return
         */
        EmbeddingStatusDto queryEmbeddingStatus(@Param("docId") Long docId);

        /**
         * 查询待向量化的问答列表,对问答做向量化,根据字段:qaStatus=-1,created在days天前,且 doc_id !=0 ; docId是0的,都是手动添加的 问答,这个方法重试是根据文档来整体检查重试的
         * 
         * @param day        天数
         * @param startIndex 索引开始位置
         * @param endIndex   索引结束位置
         * @return
         */
        List<KnowledgeQaSegment> queryListForEmbeddingQaAndEmbeddings(@Param("day") Integer day,
                        @Param("startIndex") Long startIndex, @Param("endIndex") Long endIndex);

        /**
         * 查询待向量化的问答列表,且rawId为空,手动添加的问答,对问答做向量化,根据字段:qaStatus=-1,created在days天前,
         * 
         * @param day
         * @param startIndex
         * @param endIndex
         * @return
         */
        List<KnowledgeQaSegment> queryListForEmbeddingQaAndEmbeddingsAndRawIdIsNull(@Param("day") Integer day,
                        @Param("startIndex") Long startIndex, @Param("endIndex") Long endIndex);

}
