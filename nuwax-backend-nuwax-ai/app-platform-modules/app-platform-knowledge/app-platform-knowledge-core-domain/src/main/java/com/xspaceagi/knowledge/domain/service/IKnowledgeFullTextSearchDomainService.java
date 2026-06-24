package com.xspaceagi.knowledge.domain.service;

import com.xspaceagi.knowledge.core.adapter.client.dto.PushResult;
import com.xspaceagi.knowledge.domain.model.fulltext.FullTextSearchRequestModel;
import com.xspaceagi.knowledge.domain.model.fulltext.FullTextSearchResultModel;
import com.xspaceagi.knowledge.domain.model.fulltext.FullTextStatsModel;
import com.xspaceagi.knowledge.domain.model.fulltext.RawSegmentFullTextModel;

import java.util.List;

/**
 * 知识库全文检索领域服务接口
 * 
 * @author system
 * @date 2025-03-31
 */
public interface IKnowledgeFullTextSearchDomainService {

    /**
     * 全文检索
     * 
     * @param request 检索请求
     * @return 检索结果列表
     */
    List<FullTextSearchResultModel> search(FullTextSearchRequestModel request);

    /**
     * 推送分段数据到全文检索系统
     * 
     * @param segments 分段数据列表
     * @return 推送结果（包含成功数量和成功的 raw_id 列表）
     */
    PushResult pushSegments(List<RawSegmentFullTextModel> segments);

    /**
     * 删除知识库的全文检索数据
     * 
     * @param kbId 知识库ID
     * @param tenantId 租户ID
     * @return 删除的数量
     */
    Long deleteByKbId(Long kbId, Long tenantId);

    /**
     * 删除文档的全文检索数据
     * 
     * @param docId 文档ID
     * @param kbId 知识库ID
     * @param tenantId 租户ID
     * @return 删除的数量
     */
    Long deleteByDocId(Long docId, Long kbId, Long tenantId);

    /**
     * 删除指定分段的全文检索数据
     * 
     * @param rawSegmentIds 分段ID列表
     * @param tenantId 租户ID
     * @return 删除的数量
     */
    Long deleteByRawIds(List<Long> rawSegmentIds, Long tenantId);

    /**
     * 更新分段文本
     * 
     * @param rawSegmentId 分段ID
     * @param newText 新文本
     * @param tenantId 租户ID
     * @param spaceId 空间ID
     * @return 更新的数量
     */
    Long updateSegmentText(Long rawSegmentId, String newText, Long tenantId, Long spaceId);

    /**
     * 获取统计信息
     * 
     * @param tenantId 租户ID
     * @param kbId 知识库ID（可选）
     * @param spaceId 空间ID（可选）
     * @return 统计信息
     */
    FullTextStatsModel getStats(Long tenantId, Long kbId, Long spaceId);

    /**
     * 验证请求参数
     * 
     * @param request 检索请求
     */
    void validateSearchRequest(FullTextSearchRequestModel request);
    
    /**
     * 查询知识库的所有分段ID（用于数据一致性检查）
     * 
     * @param kbId 知识库ID
     * @param tenantId 租户ID
     * @param spaceId 空间ID（可选）
     * @return 分段ID列表
     */
    List<Long> queryAllSegmentIds(Long kbId, Long tenantId, Long spaceId);

    /**
     * 查询知识库/文档的所有分段ID
     *
     * @param kbId 知识库ID
     * @param tenantId 租户ID
     * @param spaceId 空间ID（可选）
     * @param docId 文档ID（可选）
     * @return 分段ID列表
     */
    List<Long> queryAllSegmentIdsByDocId(Long kbId, Long tenantId, Long spaceId, Long docId);
}
