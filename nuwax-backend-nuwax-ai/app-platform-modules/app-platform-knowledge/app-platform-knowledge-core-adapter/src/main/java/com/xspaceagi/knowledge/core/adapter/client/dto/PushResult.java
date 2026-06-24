package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 知识库数据推送结果
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class PushResult implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 成功索引的文档数量
     */
    @JsonProperty("indexed_count")
    private Long indexedCount;
    
    /**
     * 推送时间
     */
    @JsonProperty("push_time")
    private String pushTime;
    
    /**
     * 成功的 raw_id 列表（用于客户端确认）
     */
    @JsonProperty("success_raw_ids")
    private List<Long> successRawIds;
}
