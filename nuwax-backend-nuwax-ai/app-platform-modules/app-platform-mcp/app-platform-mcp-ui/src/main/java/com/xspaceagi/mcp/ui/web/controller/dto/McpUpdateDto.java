package com.xspaceagi.mcp.ui.web.controller.dto;

import com.xspaceagi.mcp.sdk.dto.McpConfigDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class McpUpdateDto {

    @Schema(description = "MCP ID")
    private Long id;

    @Schema(description = "MCP名称")
    @NotNull
    private String name;

    @Schema(description = "MCP描述")
    @NotNull
    private String description;

    @Schema(description = "MCP图标")
    private String icon;

    @Schema(description = "MCP配置")
    @NotNull
    private McpConfigDto mcpConfig;

    @Schema(description = "是否部署")
    private boolean withDeploy;
}
