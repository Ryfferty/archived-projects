package com.xspaceagi.mcp.sdk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class McpPromptDto {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "输入参数")
    private List<McpArgDto> inputArgs;

    @Schema(description = "输出参数")
    private List<McpArgDto> outputArgs;
}
