package com.xspaceagi.agent.core.adapter.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserAgentSortDto {
    private Long id;
    private Long tenantId;
    private Long userId;
    private String category;
    private Integer sort;
    private List<Long> agentSortConfig;
    private String modified;
    private String created;
}