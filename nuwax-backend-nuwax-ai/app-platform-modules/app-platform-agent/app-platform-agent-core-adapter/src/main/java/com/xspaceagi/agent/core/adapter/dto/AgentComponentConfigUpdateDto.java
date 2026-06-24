package com.xspaceagi.agent.core.adapter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentComponentConfigUpdateDto<T> {

    @Schema(description = "组件配置ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id; // 组件配置ID

    @Schema(description = "组件名称")
    private String name;

    @Schema(description = "组件图标")
    private String icon;

    @Schema(description = "组件描述")
    private String description;

    @Schema(description = "目标组件ID")
    private Long targetId;

    @Schema(description = "绑定组件配置，不同组件配置不一样")
    private T bindConfig; //绑定组件配置

    private Integer exceptionOut; // 异常是否抛出，中断主要流程

    private String fallbackMsg; // 异常时兜底内容

}
