package com.xspaceagi.domain.model.valueobj;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import com.xspaceagi.log.spec.FlexibleDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 智能体调试大模型的日志结构定义
 * 对应 Rust 的 AgentLogEntry 结构
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AgentLogEntry {

    /**
     * 请求ID，唯一标识一次请求（必填）
     */
    private String requestId;

    /**
     * 消息ID，（必填）
     */
    private String messageId;

    /**
     * 会话ID（必填）
     */
    private String conversationId;

    /**
     * 智能体ID
     */
    private String agentId;

    /**
     * 用户UID（必填）
     */
    private String userUid;

    /**
     * 租户ID，用于租户隔离（必填）
     */
    private String tenantId;

    /**
     * 空间ID，用户可以有多个空间
     */
    private String spaceId;

    /**
     * 用户输入的内容
     */
    private String userInput;

    /**
     * 系统输出的内容
     */
    private String output;

    /**
     * 执行结果,扩展字段,字段类型存储的是json文本
     */
    private String executeResult;

    /**
     * 输入token数量
     */
    private Integer inputToken;

    /**
     * 输出token数量
     */
    private Integer outputToken;

    /**
     * 请求开始时间
     */
    @JsonDeserialize(using = FlexibleDateTimeDeserializer.class)
    private LocalDateTime requestStartTime;

    /**
     * 请求结束时间
     */
    @JsonDeserialize(using = FlexibleDateTimeDeserializer.class)
    private LocalDateTime requestEndTime;

    /**
     * 耗时(毫秒)
     */
    private Long elapsedTimeMs;

    /**
     * 节点类型
     */
    private String nodeType;

    /**
     * 节点状态
     */
    private String status;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 创建时间
     */
    @JsonDeserialize(using = FlexibleDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @JsonDeserialize(using = FlexibleDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String userName;
}