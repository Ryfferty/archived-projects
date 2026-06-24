package com.xspaceagi.agent.core.adapter.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class BindArg implements Serializable {

    @Schema(description = "值引用类型，VariableReference 变量引用；NodeReference 工作流节点引用")
    private ValueType type;

    @Schema(description = "参数值，当类型为节点引用时，示例 1.xxx 绑定节点ID为1的xxx字段")
    private String bindName;
    public enum ValueType {
        VariableReference, // 变量引用
        NodeReference// 节点引用
    }
}
