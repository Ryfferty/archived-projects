package com.xspaceagi.log.sdk.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志搜索请求参数
 * 对应 Rust 的 AgentLogSearchParams 结构
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentLogDetailParamsRequest {

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 智能体ID
     */
    private String agentId;


}