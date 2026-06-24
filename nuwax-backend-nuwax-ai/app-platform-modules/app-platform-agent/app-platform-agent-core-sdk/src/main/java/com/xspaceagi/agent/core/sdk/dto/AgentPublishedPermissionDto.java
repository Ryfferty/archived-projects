package com.xspaceagi.agent.core.sdk.dto;

import lombok.Data;

@Data
public class AgentPublishedPermissionDto {

    //可否查看
    private boolean view;

    //可否执行
    private boolean execute;

    //是否可以复制
    private boolean copy;
}
