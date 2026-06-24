package com.xspaceagi.agent.web.ui.controller.dto;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TemplateCopyDto {

    @Schema(description = "目标类型")
    private Published.TargetType targetType;

    @Schema(description = "目标ID")
    private Long targetId;

    @Schema(description = "目标空间ID")
    private Long targetSpaceId;
}
