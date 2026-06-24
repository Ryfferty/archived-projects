package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 知识库搜索请求参数
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class KnowledgeSearchParams implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 搜索关键词（全文检索）- 必填
     */
    @JsonProperty("query")
    private String query;
    
    /**
     * 租户ID（必填）
     */
    @JsonProperty("tenant_id")
    private Long tenantId;
    
    /**
     * 知识库ID列表（OR关系），可选
     */
    @JsonProperty("kb_ids")
    private List<Long> kbIds;
    
    /**
     * 文档ID列表（OR关系），可选
     */
    @JsonProperty("doc_ids")
    private List<Long> docIds;
    
    /**
     * 原始分段ID列表（OR关系），可选
     */
    @JsonProperty("raw_ids")
    private List<Long> rawIds;
    
    /**
     * 空间ID（可选）
     */
    @JsonProperty("space_id")
    private Long spaceId;
    
    /**
     * 返回数量限制
     */
    @JsonProperty("limit")
    private Long limit;
    
    /**
     * 分页偏移量
     */
    @JsonProperty("offset")
    private Long offset;
    
    /**
     * 排序字段
     */
    @JsonProperty("sort_by")
    private String sortBy;
    
    /**
     * 排序方向（asc/desc）
     */
    @JsonProperty("sort_order")
    private String sortOrder;
}
