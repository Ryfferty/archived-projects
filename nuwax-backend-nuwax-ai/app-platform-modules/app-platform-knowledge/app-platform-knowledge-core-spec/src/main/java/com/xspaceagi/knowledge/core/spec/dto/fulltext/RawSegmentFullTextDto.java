package com.xspaceagi.knowledge.core.spec.dto.fulltext;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 原始分段全文检索 DTO
 * 
 * <p>用于将原始分段数据插入到 Milvus 全文检索 Collection</p>
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawSegmentFullTextDto {

    /**
     * 原始分段ID（作为 Milvus 主键）
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
     * 原始分段文本（用于全文检索）
     */
    private String rawText;

    /**
     * 分段排序索引
     */
    private Integer sortIndex;

}

