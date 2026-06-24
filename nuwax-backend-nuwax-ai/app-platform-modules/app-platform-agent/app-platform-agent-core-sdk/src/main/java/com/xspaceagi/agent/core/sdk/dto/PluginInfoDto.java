package com.xspaceagi.agent.core.sdk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PluginInfoDto implements Serializable {

    @Schema(description = "工作流ID")
    private Long id;

    @Schema(description = "空间ID")
    private Long spaceId;

    @Schema(description = "创建者ID")
    private Long creatorId;

    @Schema(description = "工作流名称")
    private String name;

    @Schema(description = "工作流描述")
    private String description;

    @Schema(description = "图标地址")
    private String icon;

    @Schema(description = "工作流入参，也就是起始节点配置的入参")
    private List<ArgDto> inputArgs;

    @Schema(description = "工作流出参，也就是结束节点配置的出参")
    private List<ArgDto> outputArgs;

    @Schema(description = "详细配置")
    private String config;

    @Schema(description = "最后编辑时间")
    private Date modified;

    @Schema(description = "创建时间")
    private Date created;
}
