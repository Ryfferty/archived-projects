package com.xspaceagi.agent.web.ui.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ModelPageRequestResultDto {

    @Schema(description = "请求ID")
    private String requestId;

    @Schema(description = "结果HTML")
    private String html;
}
