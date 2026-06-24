package com.xspaceagi.agent.core.sdk.dto;

import com.xspaceagi.agent.core.sdk.enums.WfExecuteResultTypeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class WorkflowExecuteResultDto implements Serializable {

    private WfExecuteResultTypeEnum type;

    private Object data;

    private String outputContent;
}
