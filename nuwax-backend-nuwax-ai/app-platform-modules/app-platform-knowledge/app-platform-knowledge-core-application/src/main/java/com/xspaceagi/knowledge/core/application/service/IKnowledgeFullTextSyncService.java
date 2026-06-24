package com.xspaceagi.knowledge.core.application.service;


import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

/**
 * 知识库全文检索同步服务接口
 * 
 * @author system
 * @date 2025-03-31
 */
public interface IKnowledgeFullTextSyncService {

    /**
     * 文档分段完成后，同步到 Quickwit
     * 
     * @param docId 文档ID
     * @param userContext 用户上下文
     */
    void syncDocumentToQuickwit(Long docId, UserContext userContext);

    /**
     * 删除文档的全文检索数据
     * 
     * @param docId 文档ID
     * @param kbId 知识库ID
     * @param userContext 用户上下文
     */
    void deleteDocumentFromQuickwit(Long docId, Long kbId, UserContext userContext);

    /**
     * 更新分段文本
     * 
     * @param rawId 分段ID
     * @param newText 新文本
     * @param userContext 用户上下文
     */
    void updateSegmentText(Long rawId, String newText, UserContext userContext);

    /**
     * 删除知识库的全文检索数据
     * 
     * @param kbId 知识库ID
     * @param userContext 用户上下文
     */
    void deleteKnowledgeBaseFromQuickwit(Long kbId, UserContext userContext);

    /**
     * 删除指定分段的全文检索数据
     * 
     * @param rawSegmentIds 分段ID列表
     * @param userContext 用户上下文
     */
    void deleteSegmentsFromQuickwit(List<Long> rawSegmentIds, UserContext userContext);
}
