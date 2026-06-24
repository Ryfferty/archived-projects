package com.xspaceagi.agent.core.infra.component.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoToolCall {
    private String toolName;
    private String arguments;
    private String resultContent;
    private boolean isError;
    private String errorMessage;
}
