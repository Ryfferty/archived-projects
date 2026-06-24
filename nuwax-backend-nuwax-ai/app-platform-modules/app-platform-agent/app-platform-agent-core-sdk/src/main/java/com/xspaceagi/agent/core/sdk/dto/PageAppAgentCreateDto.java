package com.xspaceagi.agent.core.sdk.dto;

import lombok.Data;

@Data
public class PageAppAgentCreateDto {
    private Long creatorId;
    private Long spaceId;
    private String name;
    private String description;
    private String icon;
    private Long projectId;
}
