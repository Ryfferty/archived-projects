package com.xspaceagi.knowledge.core.spec.dto.fulltext;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全文检索结果 DTO
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullTextSearchResultDto {

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
     * 相关性分数（BM25 算法计算）
     */
    private Float score;

    /**
     * 文档名称（扩展字段）
     */
    private String documentName;

}

