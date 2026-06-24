package com.xspaceagi.agent.web.ui.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AKDeleteDto {

    @Schema(description = "智能体ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long agentId;

    @Schema(description = "APIKEY", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accessKey;
}
