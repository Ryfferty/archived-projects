package com.xspaceagi.agent.web.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ConversationCreateDto implements Serializable {

    @Schema(description = "智能体ID")
    private Long agentId;

    @Schema(description = "用户填写的会话变量内容")
    private Map<String, Object> variables;

    @Schema(description = "开发模式")
    private boolean devMode;
}
