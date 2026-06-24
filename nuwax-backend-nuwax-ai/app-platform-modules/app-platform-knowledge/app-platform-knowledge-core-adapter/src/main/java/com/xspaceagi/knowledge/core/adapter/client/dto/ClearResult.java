package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 知识库全量清空结果
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class ClearResult implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 清空前的总文档数
     */
    @JsonProperty("total_count_before")
    private Long totalCountBefore;
    
    /**
     * 实际删除的文档数
     */
    @JsonProperty("deleted_count")
    private Long deletedCount;
    
    /**
     * 清空操作时间
     */
    @JsonProperty("clear_time")
    private String clearTime;
}
