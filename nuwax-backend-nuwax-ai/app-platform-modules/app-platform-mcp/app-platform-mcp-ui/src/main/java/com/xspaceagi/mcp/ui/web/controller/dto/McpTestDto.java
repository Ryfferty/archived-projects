package com.xspaceagi.mcp.ui.web.controller.dto;

import com.xspaceagi.mcp.sdk.dto.McpExecuteRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
public class McpTestDto {

    private String requestId;

    private Long id;

    @Schema(description = "执行类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private McpExecuteRequest.ExecuteTypeEnum executeType;

    @Schema(description = "MCP工具/资源/提示词名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "参数")
    private Map<String, Object> params;
}
