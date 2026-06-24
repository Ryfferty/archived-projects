package com.xspaceagi.mcp.sdk.dto;

import com.xspaceagi.mcp.sdk.enums.McpContentTypeEnum;
import lombok.Data;

@Data
public class McpPromptContent extends McpContent {
    private String role;
    private McpContent content;

    @Override
    public McpContentTypeEnum getType() {
        return McpContentTypeEnum.PROMPT;
    }
}
