package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 知识库数据删除参数
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class DeleteParams implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 租户ID（必填）
     */
    @JsonProperty("tenant_id")
    private Long tenantId;
    
    /**
     * 知识库ID列表（如果只提供这一个参数，不能为空）
     */
    @JsonProperty("kb_id")
    private List<Long> kbId;
    
    /**
     * 文档ID列表（支持批量删除）
     */
    @JsonProperty("doc_id")
    private List<Long> docId;
    
    /**
     * 原始分段ID列表
     */
    @JsonProperty("raw_ids")
    private List<Long> rawIds;
    
    /**
     * 空间ID列表（支持批量删除）
     */
    @JsonProperty("space_id")
    private List<Long> spaceId;
}
