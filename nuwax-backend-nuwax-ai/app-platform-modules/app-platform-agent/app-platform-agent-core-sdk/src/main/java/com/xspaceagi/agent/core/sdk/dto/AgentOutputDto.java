package com.xspaceagi.agent.core.sdk.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class AgentOutputDto {

    private String requestId;

    private String eventType;

    private String error;

    private String data;

    private boolean completed;

    public boolean isError() {
        return StringUtils.isNotBlank(error);
    }
}
