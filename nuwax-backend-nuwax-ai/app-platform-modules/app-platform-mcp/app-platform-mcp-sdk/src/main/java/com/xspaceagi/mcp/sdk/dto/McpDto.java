package com.xspaceagi.mcp.sdk.dto;

import com.xspaceagi.mcp.sdk.enums.DeployStatusEnum;
import com.xspaceagi.mcp.sdk.enums.InstallTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class McpDto {

    @Schema(description = "MCP ID")
    private Long id;

    @Schema(description = "空间ID")
    private Long spaceId;

    @Schema(description = "创建者ID")
    private Long creatorId;

    @Schema(description = "MCP唯一标识")
    private String uid;

    @Schema(description = "MCP名称")
    private String name;

    @Schema(description = "MCP Server英文名称", hidden = true)
    private String serverName;

    @Schema(description = "MCP描述")
    private String description;

    @Schema(description = "MCP图标")
    private String icon;

    @Schema(description = "MCP分类，例如 Other")
    private String category;

    @Schema(description = "MCP安装方式")
    private InstallTypeEnum installType;

    @Schema(description = "MCP部署状态")
    private DeployStatusEnum deployStatus;

    @Schema(description = "MCP配置信息")
    private McpConfigDto mcpConfig;

    @Schema(description = "MCP已部署配置信息", hidden = true)
    private McpConfigDto deployedConfig;

    @Schema(description = "MCP部署时间")
    private Date deployed;

    @Schema(description = "MCP修改时间")
    private Date modified;

    @Schema(description = "MCP创建时间")
    private Date created;

    @Schema(description = "MCP创建者信息")
    private CreatorDto creator;

    private List<String> permissions;

    private boolean isPlatformMcp;
}
