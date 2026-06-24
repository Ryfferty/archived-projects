package com.xspaceagi.knowledge.core.adapter.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 知识库文档分段结构
 * 
 * <p>对应 Quickwit 索引中的文档结构</p>
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class KnowledgeRawSegment implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 分段ID（可选，如果不提供则自动生成 UUID v7）
     */
    @JsonProperty("id")
    private String id;
    
    /**
     * 原始分段ID（必填，对应 MySQL 表 knowledge_raw_segment 的主键 id）
     */
    @JsonProperty("raw_id")
    private Long rawId;
    
    /**
     * 知识库ID（必填）
     */
    @JsonProperty("kb_id")
    private Long kbId;
    
    /**
     * 文档ID（必填）
     */
    @JsonProperty("doc_id")
    private Long docId;
    
    /**
     * 原始文本内容（全文检索核心字段）
     */
    @JsonProperty("raw_txt")
    private String rawTxt;
    
    /**
     * 排序索引，在归属同一个文档下，段的排序
     */
    @JsonProperty("sort_index")
    private Integer sortIndex;
    
    /**
     * 租户ID（必填，用于多租户隔离）
     */
    @JsonProperty("tenant_id")
    private Long tenantId;
    
    /**
     * 空间ID
     */
    @JsonProperty("space_id")
    private Long spaceId;
    
    /**
     * 创建时间（时间戳字段）
     */
    @JsonProperty("created")
    private LocalDateTime created;
}
