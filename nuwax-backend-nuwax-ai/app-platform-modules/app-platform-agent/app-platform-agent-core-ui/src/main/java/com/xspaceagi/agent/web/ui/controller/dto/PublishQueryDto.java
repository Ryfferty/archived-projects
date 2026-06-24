package com.xspaceagi.agent.web.ui.controller.dto;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import lombok.Data;

@Data
public class PublishQueryDto {

    private Published.TargetType targetType;

    private Long targetId;
}
