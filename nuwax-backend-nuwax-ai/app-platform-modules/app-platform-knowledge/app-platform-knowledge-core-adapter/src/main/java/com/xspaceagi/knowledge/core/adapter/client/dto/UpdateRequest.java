package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 知识库文本更新请求
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class UpdateRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 分段ID（可选，与 raw_id 配合使用）
     */
    @JsonProperty("id")
    private Long id;
    
    /**
     * 原始分段ID（必填，用于定位要更新的分段）
     */
    @JsonProperty("raw_id")
    private Long rawId;
    
    /**
     * 新的文本内容（必填）
     */
    @JsonProperty("raw_txt")
    private String rawTxt;
    
    /**
     * 租户ID（必填）
     */
    @JsonProperty("tenant_id")
    private Long tenantId;
    
    /**
     * 空间ID（可选）
     */
    @JsonProperty("space_id")
    private Long spaceId;
}
