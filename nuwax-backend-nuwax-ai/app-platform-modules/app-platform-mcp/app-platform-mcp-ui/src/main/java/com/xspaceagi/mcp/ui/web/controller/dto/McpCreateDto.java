package com.xspaceagi.mcp.ui.web.controller.dto;

import com.xspaceagi.mcp.sdk.dto.McpConfigDto;
import com.xspaceagi.mcp.sdk.enums.InstallTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class McpCreateDto {

    @Schema(description = "空间ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long spaceId;

    @Schema(description = "MCP名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String name;

    @Schema(description = "MCP描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String description;

    @Schema(description = "MCP图标")
    private String icon;

    @Schema(description = "MCP安装方式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private InstallTypeEnum installType;

    @Schema(description = "MCP配置", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private McpConfigDto mcpConfig;

    @Schema(description = "是否部署")
    private boolean withDeploy;
}
