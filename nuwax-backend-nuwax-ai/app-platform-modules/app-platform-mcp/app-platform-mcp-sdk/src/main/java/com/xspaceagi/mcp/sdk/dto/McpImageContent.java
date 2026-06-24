package com.xspaceagi.mcp.sdk.dto;

import com.xspaceagi.mcp.sdk.enums.McpContentTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class McpImageContent extends McpContent {

    private List<String> audience;
    private Double priority;
    private String mimeType;

    @Override
    public McpContentTypeEnum getType() {
        return McpContentTypeEnum.IMAGE;
    }
}
