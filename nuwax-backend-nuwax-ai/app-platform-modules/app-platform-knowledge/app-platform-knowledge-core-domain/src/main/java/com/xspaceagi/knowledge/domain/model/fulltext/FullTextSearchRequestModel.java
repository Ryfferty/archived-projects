package com.xspaceagi.knowledge.domain.model.fulltext;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 全文检索请求 Domain 模型
 * 
 * @author system
 * @date 2025-03-31
 */
@Data
public class FullTextSearchRequestModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 知识库ID（单个，兼容旧版本）
     */
    private Long kbId;
    
    /**
     * 知识库ID列表（支持多个知识库检索，不传则检索所有知识库）
     */
    private List<Long> kbIds;

    /**
     * 查询文本
     */
    private String queryText;

    /**
     * 返回结果数量
     */
    private Integer topK = 10;

    /**
     * 指定文档ID列表
     */
    private List<Long> docIds;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 空间ID
     */
    private Long spaceId;

    /**
     * 分页偏移量
     */
    private Long offset = 0L;

    /**
     * 是否忽略文档发布状态
     */
    private Boolean ignoreDocStatus = false;
}
