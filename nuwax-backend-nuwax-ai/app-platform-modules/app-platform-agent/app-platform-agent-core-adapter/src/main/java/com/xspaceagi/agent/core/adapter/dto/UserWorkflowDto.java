package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserWorkflowDto extends UserTargetRelationDto implements Serializable {

    @Schema(description = "工作流ID")
    private Long workflowId;

    @Schema(description = "所属空间ID")
    private Long spaceId;
}
