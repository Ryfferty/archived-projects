package com.xspaceagi.agent.core.adapter.dto.config;

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
public class AgentComponentConfigDto {

    @Schema(description = "组件配置ID")
    private Long id; // 组件配置ID

    @Schema(description = "商户ID")
    private Long tenantId; // 商户ID

    @Schema(description = "组件名称")
    private String name;

    @Schema(description = "组件图标")
    private String icon;

    @Schema(description = "组件描述")
    private String description;

    @Schema(description = "关联的AgentID")
    private Long agentId; // AgentID

    @Schema(description = "组件类型")
    private AgentComponentConfig.Type type; // 组件类型，可选值：Plugin, Workflow, Trigger, Knowledge, Variable, Database

    @Schema(description = "绑定组件配置，不同组件配置不一样")
    private Object bindConfig; //绑定组件配置

    @Schema(description = "关联的组件ID")
    private Long targetId; // 关联的组件ID

    @Schema(description = "组件原始配置")
    private Object targetConfig; // 组件原始配置

    private Integer exceptionOut; // 异常是否抛出，中断主要流程

    private String fallbackMsg; // 异常时兜底内容

    private Date modified; // 更新时间

    private Date created; // 创建时间

    @Schema(description = "是否删除", hidden = true)
    private boolean deleted; // 是否删除

    @Schema(description = "空间id", hidden = true)
    private Long spaceId;

    @Schema(description = "分组名称")
    private String groupName;

    @Schema(description = "分组描述")
    private String groupDescription;

}
