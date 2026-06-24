package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 查询分段ID列表响应结果
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SegmentIdsResult {
    
    /**
     * 租户ID
     */
    @JsonProperty("tenant_id")
    private Long tenantId;
    
    /**
     * 知识库ID
     */
    @JsonProperty("kb_id")
    private Long kbId;
    
    /**
     * 空间ID
     */
    @JsonProperty("space_id")
    private Long spaceId;
    
    /**
     * 总分段数量
     */
    @JsonProperty("total_count")
    private Long totalCount;
    
    /**
     * 分段ID列表
     */
    @JsonProperty("segment_ids")
    private List<Long> segmentIds;
    
    /**
     * 查询时间
     */
    @JsonProperty("query_time")
    private String queryTime;
}
