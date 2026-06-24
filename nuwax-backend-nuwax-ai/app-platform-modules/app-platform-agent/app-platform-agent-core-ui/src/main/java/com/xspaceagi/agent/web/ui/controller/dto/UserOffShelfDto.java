package com.xspaceagi.agent.web.ui.controller.dto;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserOffShelfDto {

    @Schema(description = "类型，智能体、插件、工作流可以下架")
    private Published.TargetType targetType;

    @Schema(description = "智能体、插件或工作流ID")
    private Long targetId;

    @Schema(description = "发布ID，下架时必填")
    private Long publishId;

    @Schema(description = "是否仅下架模板，默认为false")
    private boolean justOffShelfTemplate;
}
