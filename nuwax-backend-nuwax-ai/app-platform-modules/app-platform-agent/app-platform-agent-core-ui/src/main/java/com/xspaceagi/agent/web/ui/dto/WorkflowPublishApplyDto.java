package com.xspaceagi.agent.web.ui.dto;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class WorkflowPublishApplyDto implements Serializable {

    @Schema(description = "工作流ID")
    private Long workflowId;

    @Schema(description = "发布范围，Space 空间；Tenant 租户全局；Global 系统全局。目前UI上的\"全局\"指的是租户全局")
    private Published.PublishScope scope;

    @Schema(description = "发布记录")
    private String remark;
}
