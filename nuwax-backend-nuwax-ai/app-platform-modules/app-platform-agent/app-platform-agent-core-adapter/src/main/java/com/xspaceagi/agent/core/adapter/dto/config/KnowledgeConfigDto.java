package com.xspaceagi.agent.core.adapter.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class KnowledgeConfigDto implements Serializable {

     @Schema(description =  "知识库ID")
    private Long knowId;

     @Schema(description =  "知识库查询关键词，可以引用上文参数")
    private String keyWord;

     @Schema(description =  "top-K值")
    private int topK;
}
