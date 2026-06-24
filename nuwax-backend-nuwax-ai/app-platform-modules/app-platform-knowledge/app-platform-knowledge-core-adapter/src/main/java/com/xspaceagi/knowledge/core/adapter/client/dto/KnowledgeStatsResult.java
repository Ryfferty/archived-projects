package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 知识库统计结果
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class KnowledgeStatsResult implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 租户 ID
     */
    @JsonProperty("tenant_id")
    private Long tenantId;
    
    /**
     * 知识库 ID
     */
    @JsonProperty("kb_id")
    private Long kbId;
    
    /**
     * 空间 ID
     */
    @JsonProperty("space_id")
    private Long spaceId;
    
    /**
     * 文档总数
     */
    @JsonProperty("doc_count")
    private Long docCount;
    
    /**
     * 分段总数
     */
    @JsonProperty("total_segments")
    private Long totalSegments;
    
    /**
     * 文档统计信息列表
     */
    @JsonProperty("doc_stats")
    private List<DocumentStats> docStats;
    
    /**
     * 统计时间
     */
    @JsonProperty("stats_time")
    private String statsTime;
}
