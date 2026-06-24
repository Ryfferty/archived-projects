package com.xspaceagi.agent.core.infra.component.agent.dto;

import com.xspaceagi.agent.core.infra.component.model.dto.ComponentExecuteResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AgentExecuteResult implements Serializable {

    private Boolean success;

    private String error;

    private Long startTime;

    private Long endTime;

    @Schema(description = "输出token数")
    private Integer completionTokens;

    @Schema(description = "提示token数")
    private Integer promptTokens;

    @Schema(description = "总token数")
    private Integer totalTokens;

    @Schema(description = "输出文本")
    private String outputText;

    @Schema(description = "组件执行结果")
    private List<ComponentExecuteResult> componentExecuteResults;

    public List<ComponentExecuteResult> getComponentExecuteResults() {
        return componentExecuteResults == null ? componentExecuteResults = new ArrayList<>() : componentExecuteResults;
    }
}
