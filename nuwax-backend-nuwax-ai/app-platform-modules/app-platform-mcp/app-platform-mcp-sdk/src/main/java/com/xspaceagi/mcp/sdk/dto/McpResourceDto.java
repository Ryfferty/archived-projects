package com.xspaceagi.mcp.sdk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class McpResourceDto {

    private String uri;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    private String mimeType;
    private McpResourceContent.Annotations annotations;

    @Schema(description = "工具输入参数")
    private List<McpArgDto> inputArgs;
}
