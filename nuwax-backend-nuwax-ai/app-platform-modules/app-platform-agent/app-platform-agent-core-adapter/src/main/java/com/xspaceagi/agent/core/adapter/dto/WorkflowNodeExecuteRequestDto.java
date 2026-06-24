package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class WorkflowNodeExecuteRequestDto implements Serializable {

    @Schema(description = "请求ID")
    private String requestId;

    @Schema(description = "工作流节点ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long nodeId;

    @Schema(description = "工作流节点参数")
    private Map<String, Object> params;
}
