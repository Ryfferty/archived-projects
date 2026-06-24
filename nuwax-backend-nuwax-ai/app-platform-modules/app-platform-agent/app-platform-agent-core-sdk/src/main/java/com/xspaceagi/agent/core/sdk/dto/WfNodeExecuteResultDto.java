package com.xspaceagi.agent.core.sdk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WfNodeExecuteResultDto implements Serializable {

    private Long nodeId;

    private String nodeName;

    private Boolean success;

    private String error;

    private Object input;

    private Object data;

    private Long startTime;

    private Long endTime;
}