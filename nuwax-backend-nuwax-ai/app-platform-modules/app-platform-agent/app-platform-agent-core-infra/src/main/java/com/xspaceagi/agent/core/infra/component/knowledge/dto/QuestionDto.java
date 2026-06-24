package com.xspaceagi.agent.core.infra.component.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

@Data
public class QuestionDto {

    @JsonPropertyDescription("生成的相关问题")
    private String question;
}
