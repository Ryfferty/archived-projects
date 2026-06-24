package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统计数据参数
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class StatsParams implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 租户ID（必填）
     */
    @JsonProperty("tenant_id")
    private Long tenantId;
    
    /**
     * 知识库ID（可选）
     */
    @JsonProperty("kb_id")
    private Long kbId;
    
    /**
     * 空间ID（可选）
     */
    @JsonProperty("space_id")
    private Long spaceId;
}
