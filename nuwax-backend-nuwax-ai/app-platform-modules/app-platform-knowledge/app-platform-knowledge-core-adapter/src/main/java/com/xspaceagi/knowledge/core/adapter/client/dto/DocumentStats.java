package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 知识库文档统计信息
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class DocumentStats implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 文档 ID
     */
    @JsonProperty("doc_id")
    private Long docId;
    
    /**
     * 文档名称
     */
    @JsonProperty("doc_name")
    private String docName;
    
    /**
     * 分段总数
     */
    @JsonProperty("segment_count")
    private Long segmentCount;
}
