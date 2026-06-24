package com.xspaceagi.agent.core.infra.component.model.dto;

import com.xspaceagi.agent.core.adapter.dto.config.Arg;
import com.xspaceagi.agent.core.spec.enums.ComponentSubTypeEnum;
import com.xspaceagi.agent.core.spec.enums.ComponentTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ComponentConfig implements Serializable {

    private Long id;

    @Schema(description = "组件名称")
    private String name;

    @Schema(description = "组件函数名", hidden = true)
    private String functionName;

    @Schema(description = "组件图标")
    private String icon;

    @Schema(description = "组件描述")
    private String description;

    @Schema(description = "组件类型")
    private ComponentTypeEnum type;

    private ComponentSubTypeEnum subType;

    @Schema(description = "组件参数，工作流、插件有效")
    private List<Arg> inputArgs;

    @Schema(description = "原始组件ID")
    private Long targetId; // 关联的组件ID

    @Schema(description = "原始组件配置")
    private Object targetConfig; // 组件原始配置

    private Object bindConfig; // 组件绑定配置，可能没有

    private Long originalTargetId; // 原始组件ID

    private boolean asyncExecute;

    private String asyncReplyContent;

    private Integer exceptionOut; // 异常是否抛出，中断主要流程

    private String fallbackMsg; // 异常时兜底内容

    //  传递原始绑定参数
    private List<Arg> bindArgs;

    // 工作流大模型节点，绑定入参传递参数
    private Map<String, Object> params;
}
