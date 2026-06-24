package com.xspaceagi.agent.core.sdk.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ConversationRpcDto {

    private Long id;
    private Long sandboxServerId;
    private Long agentId;
    private Date modified;
}
