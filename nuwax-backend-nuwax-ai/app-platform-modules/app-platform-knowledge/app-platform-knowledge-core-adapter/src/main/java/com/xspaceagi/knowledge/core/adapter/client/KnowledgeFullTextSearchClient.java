package com.xspaceagi.knowledge.core.adapter.client;

import com.xspaceagi.knowledge.core.adapter.client.dto.*;

/**
 * 知识库全文检索 HTTP Client 接口
 * 
 * <p>基于 Quickwit 服务的全文检索客户端，提供知识库分段数据的推送、检索、删除等功能</p>
 * 
 * @author system
 * @date 2025-03-31
 */
public interface KnowledgeFullTextSearchClient {
    
    /**
     * 批量推送分段数据到 Quickwit
     * 
     * @param request 推送请求（包含分段数据列表）
     * @return 推送结果（包含成功索引的文档数量）
     */
    PushResult pushSegments(PushRequest request);
    
    /**
     * 全文检索
     * 
     * @param params 检索参数（包含查询关键词、租户ID等）
     * @return 检索结果（包含匹配的分段列表）
     */
    KnowledgeSearchResult search(KnowledgeSearchParams params);
    
    /**
     * 删除数据
     * 
     * @param params 删除参数（支持按知识库、文档、分段ID删除）
     * @return 删除结果（包含删除的文档数量）
     */
    DeleteResult deleteSegments(DeleteParams params);
    
    /**
     * 更新分段文本
     * 
     * @param request 更新请求（包含分段ID和新文本）
     * @return 更新结果（包含更新的文档数量）
     */
    UpdateResult updateSegment(UpdateRequest request);
    
    /**
     * 获取统计信息
     * 
     * @param params 统计参数（租户ID、知识库ID等）
     * @return 统计结果（包含文档数、分段数等）
     */
    KnowledgeStatsResult getStats(StatsParams params);
    
    /**
     * 全量清空（慎用）
     * 
     * @return 清空结果（包含清空前的文档数和删除数量）
     */
    ClearResult clearAll();
    
    /**
     * 创建索引
     * 
     * @return 是否创建成功
     */
    boolean createIndex();
    
    /**
     * 查询知识库的所有分段ID（用于数据一致性检查）
     * 
     * @param params 查询参数（租户ID、知识库ID等）
     * @return 分段ID查询结果
     */
    SegmentIdsResult querySegmentIds(SegmentIdsParams params);
}
