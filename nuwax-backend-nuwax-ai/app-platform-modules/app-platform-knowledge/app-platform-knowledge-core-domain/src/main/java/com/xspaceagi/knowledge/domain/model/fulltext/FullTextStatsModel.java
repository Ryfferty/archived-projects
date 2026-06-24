package com.xspaceagi.knowledge.domain.model.fulltext;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 全文检索统计信息 Domain 模型
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class FullTextStatsModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 知识库ID
     */
    private Long kbId;

    /**
     * 空间ID
     */
    private Long spaceId;

    /**
     * 文档总数
     */
    private Long docCount;

    /**
     * 分段总数
     */
    private Long totalSegments;

    /**
     * 文档统计信息列表
     */
    private List<DocumentStatsModel> docStats;

    /**
     * 统计时间
     */
    private String statsTime;

    /**
     * 文档统计信息
     */
    @Data
    public static class DocumentStatsModel implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 文档ID
         */
        private Long docId;

        /**
         * 文档名称
         */
        private String docName;

        /**
         * 分段数量
         */
        private Long segmentCount;
    }
}
