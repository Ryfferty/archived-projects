package com.xspaceagi.agent.core.adapter.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QaDto implements Serializable {

    private int askTimes;

    private String originMessage;

    private String question;

    private String answer;

    private String requestId;

    private Long nodeId;

    private Long workflowId;
}
