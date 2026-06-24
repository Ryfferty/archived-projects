package com.xspaceagi.knowledge.sdk.sevice;

import com.xspaceagi.knowledge.sdk.request.KnowledgeQaRequestVo;
import com.xspaceagi.knowledge.sdk.response.KnowledgeQaResponseVo;

/**
 * Q&A搜索接口
 */
public interface IKnowledgeQaSearchRpcService {


    /**
     * Q&A搜索
     *
     * @param requestVo 请求参数
     * @return 列表
     */
    public KnowledgeQaResponseVo search(KnowledgeQaRequestVo requestVo);

}
