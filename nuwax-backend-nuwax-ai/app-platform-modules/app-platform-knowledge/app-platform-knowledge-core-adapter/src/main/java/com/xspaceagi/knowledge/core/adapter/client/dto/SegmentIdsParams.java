package com.xspaceagi.knowledge.core.adapter.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询分段ID列表请求参数
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SegmentIdsParams {
    
    /**
     * 租户ID（必填，多租户隔离）
     */
    private Long tenantId;
    
    /**
     * 知识库ID（必填）
     */
    private Long kbId;
    
    /**
     * 空间ID（可选，额外过滤条件）
     */
    private Long spaceId;

    /**
     * 文档ID（可选，额外过滤条件）
     */
    private Long docId;
}
