package com.xspaceagi.agent.core.sdk.dto;

import lombok.Data;

@Data
public class PageAppAgentUpdateDto {
    private Long agentId;
    private String name;
    private String description;
    private String icon;
}
