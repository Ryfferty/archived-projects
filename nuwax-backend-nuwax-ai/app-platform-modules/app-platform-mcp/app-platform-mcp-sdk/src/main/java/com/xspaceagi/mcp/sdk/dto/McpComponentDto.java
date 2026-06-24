package com.xspaceagi.mcp.sdk.dto;

import com.xspaceagi.mcp.sdk.enums.McpComponentTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class McpComponentDto {

    @Schema(description = "组件名称")
    private String name;

    @Schema(description = "组件图标")
    private String icon;

    @Schema(description = "组件描述")
    private String description;

    @Schema(description = "组件类型")
    private McpComponentTypeEnum type;

    @Schema(description = "关联的组件ID")
    private Long targetId; // 关联的组件ID

    @Schema(description = "组件原始配置")
    private String targetConfig; // 组件原始配置

    @Schema(description = "对于通用智能体参数绑定配置")
    private String targetBindConfig; // 组件原始配置

    @Schema(description = "工具名称", hidden = true)
    private String toolName;
}
