package com.xspaceagi.knowledge.sdk.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 知识库全文检索响应 VO
 * 
 * @author system
 * @date 2025-03-31
 */
@Getter
@Setter
@Schema(description = "知识库全文检索响应")
public class KnowledgeFullTextSearchResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "检索结果列表（按相关性分数降序排列）")
    private List<KnowledgeFullTextSearchResultVo> results;

    @Schema(description = "总耗时（毫秒）")
    private Long costTimeMs;

}

