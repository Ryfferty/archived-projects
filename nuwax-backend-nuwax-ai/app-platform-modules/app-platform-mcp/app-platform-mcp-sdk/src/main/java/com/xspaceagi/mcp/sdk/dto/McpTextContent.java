package com.xspaceagi.mcp.sdk.dto;

import com.xspaceagi.mcp.sdk.enums.McpContentTypeEnum;

public class McpTextContent extends McpContent {
    @Override
    public McpContentTypeEnum getType() {
        return McpContentTypeEnum.TEXT;
    }
}
