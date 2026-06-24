package com.xspaceagi.agent.core.infra.component.model.dto;

import com.xspaceagi.agent.core.spec.enums.ComponentTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ComponentExecuteResult implements Serializable {

    private Long id;

    private String name;

    private String icon;

    private ComponentTypeEnum type;

    private Boolean success;

    private String error;

    private Object data;

    private Object innerExecuteInfo;

    private Long startTime;

    private Long endTime;

    private Object input;

    private String executeId;

    // for tool call 文件操作类型
    private String kind;
    private List<Object> locations;
}
