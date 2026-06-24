package com.xspaceagi.agent.core.adapter.dto.config;

import com.xspaceagi.agent.core.spec.enums.NodeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NodeConfigDto<T> implements Serializable {

     @Schema(description =  "节点ID")
    private Long nodeId;

     @Schema(description =  "节点对应的agent插件或工具ID")
    private Long targetId;

     @Schema(description =  "节点备注名称")
    private String name;

     @Schema(description =  "节点类型，Tool工具, Plugin插件, Agent智能体")
    private NodeTypeEnum nodeType;

     @Schema(description =  "关联的插件Agent或工具配置")
    private T targetConfig;

     @Schema(description =  "绑定上文参数，可以是前面节点的入参或执行结果")
    private List<BindArgDto> bindArgs;

     @Schema(description =  "绑定上文参数文本类型，可以是前面节点的入参或执行结果")
    private String textArg;

     @Schema(description =  "异常是否抛出，中断主要流程")
    private Boolean exceptionOut;

    //预留，后续做成完全的工作流
     @Schema(description =  "后续执行的流程节点", hidden = true)
    List<NodeConfigDto<T>> nextNodeConfigs;
}
