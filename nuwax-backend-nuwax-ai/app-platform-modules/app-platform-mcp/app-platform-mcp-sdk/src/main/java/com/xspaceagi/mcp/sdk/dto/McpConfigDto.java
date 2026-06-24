package com.xspaceagi.mcp.sdk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class McpConfigDto {

    @Schema(description = "MCP服务配置，installType为npx、uvx、sse时有效")
    private String serverConfig;

    @Schema(description = "MCP组件配置，installType为component时有效")
    private List<McpComponentDto> components;

    @Schema(description = "MCP工具列表，无需前端传递")
    private List<McpToolDto> tools;

    @Schema(description = "MCP资源列表，无需前端传递")
    private List<McpResourceDto> resources;

    @Schema(description = "MCP提示词列表，无需前端传递")
    private List<McpPromptDto> prompts;
}