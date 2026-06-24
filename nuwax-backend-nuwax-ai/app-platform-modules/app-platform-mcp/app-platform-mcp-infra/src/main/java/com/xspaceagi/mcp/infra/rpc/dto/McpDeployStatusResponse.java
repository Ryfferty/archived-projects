package com.xspaceagi.mcp.infra.rpc.dto;

import com.xspaceagi.mcp.infra.rpc.enums.McpDeployStatusEnum;
import lombok.Data;

@Data
public class McpDeployStatusResponse {

    private Boolean ready;
    private McpDeployStatusEnum status;
    private String message;
}
