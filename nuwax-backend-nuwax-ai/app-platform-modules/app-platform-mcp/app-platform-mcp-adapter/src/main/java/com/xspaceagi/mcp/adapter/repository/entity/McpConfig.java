package com.xspaceagi.mcp.adapter.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.mcp.sdk.enums.DeployStatusEnum;
import com.xspaceagi.mcp.sdk.enums.InstallTypeEnum;
import lombok.Data;

import java.util.Date;

@Data
@TableName("mcp_config")
public class McpConfig {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "_tenant_id")
    private Long tenantId;

    private Long spaceId;

    private Long creatorId;
    private String uid;
    private String name;
    private String serverName;

    private String description;
    private String category;
    private String icon;

    private InstallTypeEnum installType;

    private DeployStatusEnum deployStatus;

    private String config;

    private String deployedConfig;

    private Date deployed;

    private Date modified;

    private Date created;
}
