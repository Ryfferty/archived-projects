package com.xspaceagi.knowledge.core.application.service;

import com.xspaceagi.knowledge.domain.model.fulltext.FullTextStatsModel;
import com.xspaceagi.system.spec.common.UserContext;

/**
 * 知识库全文检索批量同步服务接口（存量数据同步）
 * 
 * @author system
 * @date 2025-03-31
 */
public interface IKnowledgeFullTextBatchSyncService {

    /**
     * 获取统计信息
     * 
     * @param kbId 知识库ID
     * @param userContext 用户上下文
     * @return 统计信息
     */
    FullTextStatsModel getStats(Long kbId, UserContext userContext);

    /**
     * 验证同步结果
     * 
     * @param tenantId 租户ID
     */
    void validateSyncResult(Long tenantId);

    /**
     * 同步所有未同步的知识库到 Quickwit（迁移专用，带状态更新）
     * 
     * @param tenantId 租户ID
     */
    void syncAllUnsyncedKnowledgeBasesToQuickwit(Long tenantId);

    /**
     * 同步单个知识库的所有分段到 Quickwit（迁移专用，带状态更新）
     * 
     * @param kbId 知识库ID
     * @param tenantId 租户ID
     * @return 同步的分段数量
     */
    long batchSyncKnowledgeBaseForMigration(Long kbId, Long tenantId);

    /**
     * 补偿同步：修复指定知识库的数据不一致
     * 
     * @param kbId 知识库ID
     * @param tenantId 租户ID
     * @return 修复的分段数量
     */
    long repairKnowledgeBaseConsistency(Long kbId, Long tenantId);
}
