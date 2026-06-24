package com.xspaceagi.mcp.sdk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class McpExecuteOutput {

    @Schema(description = "执行结果状态")
    private boolean success;

    private String message;

    private List<McpContent> result;
}
