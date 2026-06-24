package com.xspaceagi.log.sdk.reponse;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class AgentLogModelResponse {

    /**
     * 请求ID，唯一标识一次请求（必填）
     */
    @Schema(description = "请求ID，唯一标识一次请求（必填）")
    private String requestId;

    /**
     * 消息ID，（必填）
     */
    @Schema(description = "消息ID，（必填）")
    private String messageId;

    /**
     * 智能体ID
     */
    @Schema(description = "智能体ID")
    private String agentId;

    /**
     * 会话ID（必填）
     */
    @Schema(description = "会话ID（必填）")
    private String conversationId;

    /**
     * 用户UID（必填）
     */
    @Schema(description = "用户UID（必填）")
    private String userUid;

    /**
     * 租户ID，用于租户隔离（必填）
     */
    @Schema(description = "租户ID，用于租户隔离（必填）")
    private String tenantId;

    /**
     * 空间ID，用户可以有多个空间
     */
    @Schema(description = "空间ID，用户可以有多个空间")
    private String spaceId;

    /**
     * 用户输入的内容
     */
    @Schema(description = "用户输入的内容")
    private String userInput;

    /**
     * 系统输出的内容
     */
    @Schema(description = "系统输出的内容")
    private String output;

    /**
     * 执行结果,扩展字段,字段类型存储的是json文本
     */
    @Schema(description = "执行结果,扩展字段,字段类型存储的是json文本")
    private String executeResult;

    /**
     * 输入token数量
     */
    @Schema(description = "输入token数量")
    private Integer inputToken;

    /**
     * 输出token数量
     */
    @Schema(description = "输出token数量")
    private Integer outputToken;

    /**
     * 请求开始时间
     */
    @Schema(description = "请求开始时间")
    private LocalDateTime requestStartTime;

    /**
     * 请求结束时间
     */
    @Schema(description = "请求结束时间")
    private LocalDateTime requestEndTime;

    /**
     * 耗时(毫秒)
     */
    @Schema(description = "耗时(毫秒)")
    private Long elapsedTimeMs;

    /**
     * 节点类型
     */
    @Schema(description = "节点类型")
    private String nodeType;

    /**
     * 节点状态
     */
    @Schema(description = "节点状态")
    private String status;

    /**
     * 节点名称
     */
    @Schema(description = "节点名称")
    private String nodeName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;
    
    /**
     * 用户名称
     */
    @Schema(description = "用户名称")
    private String userName;
}