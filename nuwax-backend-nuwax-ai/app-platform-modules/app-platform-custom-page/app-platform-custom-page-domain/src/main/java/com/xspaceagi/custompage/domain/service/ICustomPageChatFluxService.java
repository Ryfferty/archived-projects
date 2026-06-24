package com.xspaceagi.custompage.domain.service;

import java.util.Map;

import com.xspaceagi.system.spec.common.UserContext;

import reactor.core.publisher.Flux;

/**
 * 前端项目聊天响应式流服务
 */
public interface ICustomPageChatFluxService {

    /**
     * 发送聊天消息（使用 Flux 响应式流）
     */
    Flux<Map<String, Object>> sendAgentChatFlux(Map<String, Object> chatBody,
            UserContext userContext);

    /**
     * 终止会话
     */
    boolean terminateSession(String sessionId);

}
