package com.xspaceagi.agent.web.ui.controller.dto;

import com.xspaceagi.system.sdk.service.dto.UserShareDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConversationShareDto {

    @Schema(description = "会话ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long conversationId;
    @Schema(description = "分享类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private UserShareDto.UserShareType type;

    @Schema(description = "分享有效期，单位秒，留空为不限制；远程桌面分享需要用户设置有效期")
    private Long expireSeconds;

    @Schema(description = "分享内容，可以是具体的文件地址")
    private String content;
}
