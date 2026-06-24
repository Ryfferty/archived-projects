package com.xspaceagi.agent.core.adapter.dto;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ImportTemplateResultDto {

    @Schema(description = "导入类型")
    private Published.TargetType targetType;

    @Schema(description = "导入生成ID")
    private Long targetId;
}
