package com.xspaceagi.mcp.adapter.application;

import com.xspaceagi.mcp.sdk.dto.McpDto;

public interface McpDeployTaskService {

    void addDeployTask(McpDto mcpDto);

    void addDeployTask(McpDto mcpDto, boolean notify);
}
