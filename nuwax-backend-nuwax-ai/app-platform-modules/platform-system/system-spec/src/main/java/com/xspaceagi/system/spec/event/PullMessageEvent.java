package com.xspaceagi.system.spec.event;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息拉取事件
 * 用于EventBus事件传递
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PullMessageEvent implements Serializable{
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 租户ID
     */
    private Long tenantId;
    
    /**
     * 客户端ID
     */
    private String clientId;
    
} 