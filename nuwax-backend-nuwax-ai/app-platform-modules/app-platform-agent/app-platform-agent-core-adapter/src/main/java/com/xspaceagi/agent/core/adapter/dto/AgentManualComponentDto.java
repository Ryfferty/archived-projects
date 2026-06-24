package com.xspaceagi.agent.core.adapter.dto;

import com.xspaceagi.agent.core.adapter.repository.entity.AgentComponentConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentManualComponentDto {

    @Schema(description = "组件ID")
    private Long id; // 组件配置ID

    @Schema(description = "组件名称")
    private String name;

    @Schema(description = "组件图标")
    private String icon;

    @Schema(description = "组件描述")
    private String description;

    @Schema(description = "组件类型")
    private AgentComponentConfig.Type type; // 组件类型，可选值：Plugin, Workflow, Trigger, Knowledge, Variable, Database

    @Schema(description = "是否默认选中，0-否，1-是")
    private Integer defaultSelected;
}
