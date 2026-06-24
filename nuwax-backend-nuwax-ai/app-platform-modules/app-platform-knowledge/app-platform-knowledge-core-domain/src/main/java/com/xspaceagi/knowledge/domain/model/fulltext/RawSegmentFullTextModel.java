package com.xspaceagi.knowledge.domain.model.fulltext;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 原始分段全文检索 Domain 模型
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
@Builder
public class RawSegmentFullTextModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原始分段ID（对应 MySQL 主键）
     */
    private Long rawId;

    /**
     * 知识库ID
     */
    private Long kbId;

    /**
     * 文档ID
     */
    private Long docId;

    /**
     * 原始文本内容
     */
    private String rawText;

    /**
     * 排序索引
     */
    private Integer sortIndex;

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
