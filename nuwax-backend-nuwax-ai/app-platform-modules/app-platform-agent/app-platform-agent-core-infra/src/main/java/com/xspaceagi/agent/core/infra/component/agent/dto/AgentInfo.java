package com.xspaceagi.agent.core.infra.component.agent.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AgentInfo implements Serializable {

    private Long id;

    private String name;

    private String description;
}
