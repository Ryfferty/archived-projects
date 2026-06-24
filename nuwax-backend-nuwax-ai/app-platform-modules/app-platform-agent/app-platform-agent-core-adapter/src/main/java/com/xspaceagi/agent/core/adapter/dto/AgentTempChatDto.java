package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentTempChatDto {

    @Schema(description = "链接ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    private Long userId;

    @Schema(description = "智能体ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long agentId;

    @Schema(description = "链接Key", hidden = true)
    private String chatKey;

    @Schema(description = "链接地址")
    private String chatUrl;

    @Schema(description = "二维码地址")
    private String qrCodeUrl;

    @Schema(description = "是否需要登录")
    private Integer requireLogin;

    @Schema(description = "链接过期时间")
    private Date expire;
}
