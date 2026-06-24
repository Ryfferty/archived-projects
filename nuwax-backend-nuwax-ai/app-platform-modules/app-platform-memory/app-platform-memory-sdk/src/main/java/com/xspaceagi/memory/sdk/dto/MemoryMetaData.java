package com.xspaceagi.memory.sdk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 记忆数据DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoryMetaData {

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 代理ID
     */
    private Long agentId;

    /**
     * 上下文
     */
    private String context;

    /**
     * 用户输入
     */
    private String userInput;

}
