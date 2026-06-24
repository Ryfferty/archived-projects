package com.xspaceagi.knowledge.domain.model.fulltext;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 全文检索结果 Domain 模型
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class FullTextSearchResultModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原始分段ID
     */
    private Long rawSegmentId;

    /**
     * 文档ID
     */
    private Long docId;

    /**
     * 知识库ID
     */
    private Long kbId;

    /**
     * 原始分段文本
     */
    private String rawText;

    /**
     * 分段排序索引
     */
    private Integer sortIndex;

    /**
     * 相关性分数（BM25算法计算）
     */
    private Float score;

    /**
     * 文档名称
     */
    private String documentName;

    /**
     * 高亮文本
     */
    private String highlight;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 空间ID
     */
    private Long spaceId;

    /**
     * 创建时间
     */
    private LocalDateTime created;
}
