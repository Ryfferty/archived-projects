package com.xspaceagi.domain.model.valueobj;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.xspaceagi.log.spec.FlexibleDateTimeDeserializer;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AgentLogSearchParams {

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 会话ID
     */
    private String conversationId;

    /**
     * 消息ID
     */
    private String messageId;
    /**
     * 智能体ID
     */
    private String agentId;

    /**
     * 用户UID
     */
    private String userUid;

    /**
     * 用户输入,需要支持全文检索，支持多个关键字（AND关系）
     */
    private List<String> userInput;

    /**
     * 系统输出,需要支持全文检索，支持多个关键字（AND关系）
     */
    private List<String> output;

    /**
     * 开始时间
     */
    @JsonDeserialize(using = FlexibleDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", shape = JsonFormat.Shape.STRING)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonDeserialize(using = FlexibleDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", shape = JsonFormat.Shape.STRING)
    private LocalDateTime endTime;

    /**
     * 租户ID，用于租户隔离，确保只查询特定租户的日志
     */
    private String tenantId;

    /**
     * 空间ID，可选，用于查询特定空间的日志，支持多个ID（OR关系）
     */
    private List<String> spaceId;

}