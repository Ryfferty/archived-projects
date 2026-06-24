package com.xspaceagi.agent.core.adapter.dto;

import com.alibaba.fastjson2.JSONObject;
import com.xspaceagi.agent.core.adapter.dto.config.workflow.WorkflowNodeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WorkflowNodeUpdateDto<T> implements Serializable {

    @Schema(description = "节点ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long nodeId;

    @Schema(description = "节点名称")
    private String name;

    @Schema(description = "节点描述")
    private String description;

    @Schema(description = "上级循环节点ID，针对循环节点有效")
    private Long loopNodeId;

    @Schema(description = "内部起始节点ID，针对循环节点有效")
    private Long innerStartNodeId;

    @Schema(description = "内部结束节点ID，针对循环节点有效")
    private Long innerEndNodeId;

    @Schema(description = "下级节点ID列表")
    private List<Long> nextNodeIds;

    @Schema(description = "下级节点ID列表，针对更新节点有效")
    private List<Long> updateNextNodeIds;

    @Schema(description = "节点详细配置信息，可以不传，但是传则需要传全部配置")
    private T nodeConfig;

    @Schema(description = "内部节点列表更新，针对循环节点有效", hidden = true)
    private List<JSONObject> innerNodes;

    @Schema(description = "节点更新之前的节点信息", hidden = true)
    private WorkflowNodeDto lastWorkflowNodeDto;
}
