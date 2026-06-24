package com.xspaceagi.log.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xspaceagi.domain.model.valueobj.AgentLogSearchParams;
import com.xspaceagi.log.sdk.request.AgentLogDetailParamsRequest;
import com.xspaceagi.log.spec.FlexibleDateTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日志搜索请求参数
 * 对应 Rust 的 AgentLogSearchParams 结构
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentLogSearchParamsRequest {

    /**
     * 请求ID
     */
    @Schema(description = "请求ID")
    private String requestId;

    /**
     * 消息ID
     */
    @Schema(description = "消息ID")
    private String messageId;

    /**
     * 智能体ID
     */
    @Schema(description = "智能体ID")
    private String agentId;

    /**
     * 会话ID
     */
    @Schema(description = "会话ID")
    private String conversationId;

    /**
     * 用户UID
     */
    @Schema(description = "用户UID")
    private String userUid;

    /**
     * 用户输入,需要支持全文检索，支持多个关键字（AND关系）
     */
    @Schema(description = "用户输入,需要支持全文检索，支持多个关键字（AND关系）")
    private List<String> userInput;

    /**
     * 系统输出,需要支持全文检索，支持多个关键字（AND关系）
     */
    @Schema(description = "系统输出,需要支持全文检索，支持多个关键字（AND关系）")
    private List<String> output;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间，支持RFC 3339格式，例如：2024-05-15T10:30:00Z")
    @JsonDeserialize(using = FlexibleDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", shape = JsonFormat.Shape.STRING)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间，支持RFC 3339格式，例如：2024-05-15T10:30:00Z")
    @JsonDeserialize(using = FlexibleDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", shape = JsonFormat.Shape.STRING)
    private LocalDateTime endTime;

    /**
     * 租户ID，用于租户隔离，确保只查询特定租户的日志
     */
    @Schema(description = "租户ID，用于租户隔离，确保只查询特定租户的日志")
    private String tenantId;

    /**
     * 空间ID，可选，用于查询特定空间的日志，支持多个ID（OR关系）
     */
    @Schema(description = "空间ID，可选，用于查询特定空间的日志，支持多个ID（OR关系）")
    private List<String> spaceId;

    public static AgentLogSearchParams convertFrom(AgentLogSearchParamsRequest request) {
        if (request == null) {
            return null;
        }
        // request 处理下面的String字段的2边的空白,使用trim(),如果不为空的话
        if (request.getRequestId() != null) {
            request.setRequestId(request.getRequestId().trim());
        }
        if (request.getMessageId() != null) {
            request.setMessageId(request.getMessageId().trim());
        }
        if (request.getAgentId() != null) {
            request.setAgentId(request.getAgentId().trim());
        }
        if (request.getConversationId() != null) {
            request.setConversationId(request.getConversationId().trim());
        }
        if (request.getUserUid() != null) {
            request.setUserUid(request.getUserUid().trim());
        }
        if (request.getTenantId() != null) {
            request.setTenantId(request.getTenantId().trim());
        }
        if (request.getSpaceId() != null) {
            var spaceId = request.getSpaceId().stream().map(item -> {
                if (item != null) {
                    item = item.trim();
                }
                return item;
            }).toList();
            request.setSpaceId(spaceId);
        }
        if (request.getUserInput() != null) {
            var userInput = request.getUserInput().stream().map(item -> {
                if (item != null) {
                    item = item.trim();
                }
                return item;
            }).toList();
            request.setUserInput(userInput);
        }
        if (request.getOutput() != null) {
            var output = request.getOutput().stream().map(item -> {
                if (item != null) {
                    item = item.trim();
                }
                return item;
            }).toList();
            request.setOutput(output);
        }
        return AgentLogSearchParams.builder()
                .requestId(request.getRequestId())
                .messageId(request.getMessageId())
                .agentId(request.getAgentId())
                .conversationId(request.getConversationId())
                .userUid(request.getUserUid())
                .tenantId(request.getTenantId())
                .spaceId(request.getSpaceId())
                .userInput(request.getUserInput())
                .output(request.getOutput())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();
    }

    public static AgentLogSearchParams convertFrom(AgentLogDetailParamsRequest request) {
        return AgentLogSearchParams.builder()
                .requestId(request.getRequestId())
                .messageId(null)
                .agentId(request.getAgentId())
                .conversationId(null)
                .userUid(null)
                .tenantId(null)
                .spaceId(null)
                .build();
    }
}