package com.xspaceagi.agent.core.infra.component.knowledge.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QaDto {

    @Schema(description = "问答ID")
    private Long qaId;

    @Schema(description = "分段问题")
    private String question;

    @Schema(description = "分段答案")
    private String answer;
}
