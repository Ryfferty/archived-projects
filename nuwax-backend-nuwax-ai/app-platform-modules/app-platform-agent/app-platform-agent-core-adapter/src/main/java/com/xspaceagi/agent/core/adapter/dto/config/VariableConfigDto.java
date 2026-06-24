package com.xspaceagi.agent.core.adapter.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VariableConfigDto implements Serializable {

    @Schema(description = "变量参数列表")
    private List<Arg> variables;

    public List<Arg> getVariables() {
        return variables == null ? variables = List.of() : variables;
    }
}
