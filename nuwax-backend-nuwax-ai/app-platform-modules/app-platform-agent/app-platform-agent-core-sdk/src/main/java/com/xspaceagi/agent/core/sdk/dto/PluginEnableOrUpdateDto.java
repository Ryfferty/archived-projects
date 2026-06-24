package com.xspaceagi.agent.core.sdk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class PluginEnableOrUpdateDto implements Serializable {

    @Schema(description = "操作的用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "空间ID")
    private Long spaceId;

    @Schema(description = "插件ID, 可选，只有更新时需要传")
    private Long pluginId;

    @Schema(description = "插件图标")
    private String icon;

    @Schema(description = "插件名称")
    private String name;

    @Schema(description = "插件配置信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String config;

    @Schema(description = "插件分类")
    private String category;

    @Schema(description = "插件启用必填参数, {}")
    private String paramJson;
}
