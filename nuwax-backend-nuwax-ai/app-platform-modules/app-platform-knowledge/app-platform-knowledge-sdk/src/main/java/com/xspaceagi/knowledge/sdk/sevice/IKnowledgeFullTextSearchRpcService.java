package com.xspaceagi.knowledge.sdk.sevice;

import com.xspaceagi.knowledge.sdk.request.KnowledgeFullTextSearchRequestVo;
import com.xspaceagi.knowledge.sdk.response.KnowledgeFullTextSearchResponseVo;

/**
 * 知识库全文检索 RPC 服务接口
 * 
 * <p>基于 Milvus BM25 算法的全文检索服务</p>
 * 
 * @author system
 * @date 2025-03-31
 */
public interface IKnowledgeFullTextSearchRpcService {

    /**
     * 全文检索（推荐使用）
     * 
     * <p>基于 BM25 算法的全文检索，支持精确关键词匹配</p>
     * 
     * <p><b>最简使用示例：</b></p>
     * <pre>
     * KnowledgeFullTextSearchRequestVo request = new KnowledgeFullTextSearchRequestVo();
     * request.setKbId(123L);
     * request.setQueryText("Spring Boot 开发框架");
     * // 其他参数使用默认值即可
     * KnowledgeFullTextSearchResponseVo response = rpcService.search(request);
     * </pre>
     * 
     * @param requestVo 检索请求参数（只需设置 kbId 和 queryText）
     * @return 检索结果列表
     */
    KnowledgeFullTextSearchResponseVo search(KnowledgeFullTextSearchRequestVo requestVo);

}

