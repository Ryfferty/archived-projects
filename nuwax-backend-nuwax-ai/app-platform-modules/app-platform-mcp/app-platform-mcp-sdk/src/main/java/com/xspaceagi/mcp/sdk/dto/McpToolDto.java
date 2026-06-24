package com.xspaceagi.mcp.sdk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class McpToolDto {

    @Schema(description = "工具名称")
    private String name;

    @Schema(description = "工具描述")
    private String description;

    @Schema(description = "工具输入参数")
    private List<McpArgDto> inputArgs;

    @Schema(description = "工具输出参数")
    private List<McpArgDto> outputArgs;

    @Schema(description = "工具原始的定义", hidden = true)
    private String jsonSchema;
}
