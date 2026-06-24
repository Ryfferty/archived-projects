package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 知识库数据删除结果
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class DeleteResult implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 删除的文档数量
     */
    @JsonProperty("deleted_count")
    private Long deletedCount;
    
    /**
     * 删除时间
     */
    @JsonProperty("delete_time")
    private String deleteTime;
}
