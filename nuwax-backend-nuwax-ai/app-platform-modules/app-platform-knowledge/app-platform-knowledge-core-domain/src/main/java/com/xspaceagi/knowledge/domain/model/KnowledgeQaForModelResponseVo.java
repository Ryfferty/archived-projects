package com.xspaceagi.knowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 大模型生成的问答
 */
@Schema(description = "大模型生成的问答")
@Getter
@Setter
public class KnowledgeQaForModelResponseVo {

    @JsonPropertyDescription("问题")
    private String question;


    @JsonPropertyDescription("答案")
    private String answer;


}
