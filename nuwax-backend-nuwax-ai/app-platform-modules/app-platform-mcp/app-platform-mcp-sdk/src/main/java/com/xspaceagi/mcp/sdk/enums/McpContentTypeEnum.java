package com.xspaceagi.mcp.sdk.enums;

public enum McpContentTypeEnum {

    TEXT("text"),
    IMAGE("image"),
    RESOURCE("resource"),
    PROMPT("prompt");
    private String value;

    McpContentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
