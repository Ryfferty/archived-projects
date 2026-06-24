package com.xspaceagi.knowledge.sdk.sevice;

import com.xspaceagi.knowledge.sdk.request.KnowledgeDocumentRequestVo;
import com.xspaceagi.knowledge.sdk.response.KnowledgeDocumentResponseVo;

/**
 * Q&A搜索接口
 */
public interface IKnowledgeDocumentSearchRpcService {


    /**
     * 文档搜索
     *
     * @param requestVo 搜索参数
     * @return 列表
     */
    public KnowledgeDocumentResponseVo documentSearch(KnowledgeDocumentRequestVo requestVo);

}
