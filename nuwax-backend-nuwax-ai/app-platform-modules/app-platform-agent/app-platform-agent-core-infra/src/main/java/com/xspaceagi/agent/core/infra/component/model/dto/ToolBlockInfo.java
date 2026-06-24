package com.xspaceagi.agent.core.infra.component.model.dto;

import lombok.Data;

@Data
public class ToolBlockInfo {
    String fullMatch;
    String id;
    String type;
    String content;

    private String functionName;

    public ToolBlockInfo(String fullMatch, String id, String type, String content, String functionName) {
        this.fullMatch = fullMatch;
        this.id = id;
        this.type = type;
        this.content = content;
        this.functionName = functionName;
    }
}