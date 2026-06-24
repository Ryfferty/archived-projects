package com.xspaceagi.custompage.ui.web.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Conversation record response DTO
 */
@Data
@Schema(description = "Conversation record response")
public class ConversationRes {

    @Schema(description = "Database ID")
    private Long id;

    @Schema(description = "Project ID")
    private Long projectId;

    @Schema(description = "Conversation ID")
    private Long conversationId;

    @Schema(description = "Conversation topic")
    private String topic;

    @Schema(description = "Conversation content")
    private String content;

    @Schema(description = "Message role: USER / ASSISTANT")
    private String role;

    @Schema(description = "Session ID")
    private String sessionId;

    @Schema(description = "Request ID")
    private String requestId;

    @Schema(description = "Created time")
    private Date created;

    @Schema(description = "Creator user ID")
    private Long creatorId;

}