package com.xspaceagi.agent.core.infra.component.table.dto;

import com.xspaceagi.agent.core.infra.component.agent.AgentContext;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
public class TableExecutorContext {

    private Long tableId;

    private String sql;

    @Schema(description = "绑定sql的参数")
    private Map<String, Object> args;

    @Schema(description = "扩展参数,比如:uid = 123, spaceId = 122,用于额外限制条件,加入到查询sql语句中")
    private Map<String, Object> extArgs;

    private AgentContext agentContext;
}
