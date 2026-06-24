package com.xspaceagi.mcp.sdk.dto;

import com.xspaceagi.mcp.sdk.enums.McpContentTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class McpResourceContent extends McpContent {
    private String uri;
    private String mimeType;
    private String blob;
    private String name;
    private String description;
    private Annotations annotations;

    @Override
    public McpContentTypeEnum getType() {
        return McpContentTypeEnum.RESOURCE;
    }

    public record Annotations(List<String> audience,
                              Double priority) {
    }
}
