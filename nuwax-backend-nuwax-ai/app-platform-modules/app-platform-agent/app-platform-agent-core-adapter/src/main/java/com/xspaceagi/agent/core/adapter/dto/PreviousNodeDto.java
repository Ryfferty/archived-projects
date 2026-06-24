package com.xspaceagi.agent.core.adapter.dto;

import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import com.xspaceagi.agent.core.adapter.repository.entity.WorkflowNodeConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PreviousNodeDto implements Serializable {

    @Schema(description = "节点ID")
    private Long id;

    @Schema(description = "节点名称")
    private String name;

    @Schema(description = "节点图标地址")
    private String icon;

    @Schema(description = "节点类型")
    private WorkflowNodeConfig.NodeType type;

    @Schema(description = "节点输出参数")
    private List<Arg> outputArgs;

    private Long loopNodeId;

    private Integer sort;

    public boolean equals(Object obj) {
        if (obj instanceof PreviousNodeDto) {
            PreviousNodeDto node = (PreviousNodeDto) obj;
            return this.id.equals(node.id);
        }
        return false;
    }
}
