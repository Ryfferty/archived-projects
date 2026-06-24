package com.xspaceagi.agent.core.adapter.dto;

import com.xspaceagi.agent.core.adapter.repository.entity.Conversation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConversationQueryDto implements Serializable {

    @Schema(description = "智能体ID，可选")
    private Long agentId;

    @Schema(description = "会话ID，可选")
    private Long lastId;

    @Schema(description = "返回会话数量，可选")
    private Integer limit;

    @Schema(description = "定时会话状态，可选")
    private Conversation.ConversationTaskStatus taskStatus;

    @Schema(description = "会话主题，可选")
    private String topic;
}
