package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 知识库数据推送请求
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class PushRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 分段数据列表（每个分段包含 tenant_id）
     */
    @JsonProperty("segments")
    private List<KnowledgeRawSegment> segments;
}
