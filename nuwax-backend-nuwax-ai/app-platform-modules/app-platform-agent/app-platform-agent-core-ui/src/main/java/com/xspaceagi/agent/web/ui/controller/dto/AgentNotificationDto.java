package com.xspaceagi.agent.web.ui.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AgentNotificationDto {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "消息内容")
    private String content;
}
