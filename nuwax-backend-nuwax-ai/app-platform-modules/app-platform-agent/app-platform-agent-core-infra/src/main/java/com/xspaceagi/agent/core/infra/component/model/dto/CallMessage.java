package com.xspaceagi.agent.core.infra.component.model.dto;

import com.xspaceagi.agent.core.adapter.dto.ChatMessageDto;
import lombok.Data;

@Data
public class CallMessage extends ChatMessageDto {

    private String id;

    private Object ext;

    private String executeId;
}
