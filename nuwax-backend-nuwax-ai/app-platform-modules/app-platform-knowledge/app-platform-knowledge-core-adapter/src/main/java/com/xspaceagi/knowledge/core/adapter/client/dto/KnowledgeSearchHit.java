package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 知识库搜索命中结果
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class KnowledgeSearchHit implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 分段ID
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * 原始分段ID（必填）
     */
    @JsonProperty("raw_id")
    private Long rawId;
    
    /**
     * 知识库ID
     */
    @JsonProperty("kb_id")
    private Long kbId;
    
    /**
     * 文档ID
     */
    @JsonProperty("doc_id")
    private Long docId;
    
    /**
     * 原始文本
     */
    @JsonProperty("raw_txt")
    private String rawTxt;
    
    /**
     * 排序索引
     */
    @JsonProperty("sort_index")
    private Long sortIndex;
    
    /**
     * 租户ID
     */
    @JsonProperty("tenant_id")
    private Long tenantId;
    
    /**
     * 空间ID
     */
    @JsonProperty("space_id")
    private Long spaceId;
    
    /**
     * 创建时间
     */
    @JsonProperty("created")
    private LocalDateTime created;
    
    /**
     * BM25 相关性评分
     */
    @JsonProperty("score")
    private Float score;
    
    /**
     * 高亮文本
     */
    @JsonProperty("highlight")
    private String highlight;
}
