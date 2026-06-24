package com.xspaceagi.agent.core.infra.component.workflow.dto;

import com.xspaceagi.agent.core.infra.component.workflow.enums.NodeExecuteStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class NodeExecutingDto implements Serializable {

    private NodeExecuteStatus status;

    private NodeExecuteResult result;

    private Long nodeId;

    private String name;
}