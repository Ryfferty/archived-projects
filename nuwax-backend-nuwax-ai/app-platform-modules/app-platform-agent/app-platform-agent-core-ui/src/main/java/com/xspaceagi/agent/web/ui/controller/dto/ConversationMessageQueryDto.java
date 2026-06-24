package com.xspaceagi.agent.web.ui.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConversationMessageQueryDto {

    @Schema(description = "会话ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long conversationId;

    @Schema(description = "查询游标")
    private Long index;

    @Schema(description = "查询数量")
    private Integer size;
}
