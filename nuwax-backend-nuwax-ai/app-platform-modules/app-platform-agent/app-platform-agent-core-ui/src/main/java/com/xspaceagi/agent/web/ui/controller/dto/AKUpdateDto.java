package com.xspaceagi.agent.web.ui.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AKUpdateDto {

    @Schema(description = "智能体ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long agentId;

    @Schema(description = "APIKEY", requiredMode = Schema.RequiredMode.REQUIRED)
    private String accessKey;

    @Schema(description = "开发模式，1 是，0 否", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer devMode;
}
