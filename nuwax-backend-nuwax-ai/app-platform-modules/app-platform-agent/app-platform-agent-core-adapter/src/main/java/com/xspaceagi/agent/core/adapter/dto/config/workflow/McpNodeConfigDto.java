package com.xspaceagi.agent.core.adapter.dto.config.workflow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * mcp节点配置
 */
@Getter
@Setter
public class McpNodeConfigDto extends NodeConfigDto {

    private Long mcpId;

    @Schema(description = "mcp工具名称")
    private String toolName;

    @Schema(description = "mcp配置", hidden = true)
    private Object mcp;
}
