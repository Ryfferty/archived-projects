package com.xspaceagi.sandbox.ui.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SandboxConfigCreateDto {

    @Schema(description = "电脑名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "电脑描述")
    private String description;
}
