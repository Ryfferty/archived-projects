package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 知识库文本更新结果
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class UpdateResult implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 更新的文档数量
     */
    @JsonProperty("updated_count")
    private Long updatedCount;
    
    /**
     * 更新时间
     */
    @JsonProperty("update_time")
    private String updateTime;
}
