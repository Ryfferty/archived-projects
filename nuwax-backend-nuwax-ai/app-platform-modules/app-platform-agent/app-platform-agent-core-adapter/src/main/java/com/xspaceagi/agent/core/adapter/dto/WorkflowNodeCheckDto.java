package com.xspaceagi.agent.core.adapter.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WorkflowNodeCheckDto implements Serializable {

    private Long nodeId;

    private boolean success;

    private List<String> messages;
}
