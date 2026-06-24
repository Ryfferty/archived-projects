package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 知识库搜索结果
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class KnowledgeSearchResult implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 搜索结果列表
     */
    @JsonProperty("results")
    private List<KnowledgeSearchHit> results;
    
    /**
     * 总匹配数量
     */
    @JsonProperty("total")
    private Long total;
    
    /**
     * 处理耗时（毫秒）
     */
    @JsonProperty("took_ms")
    private Long tookMs;
}
