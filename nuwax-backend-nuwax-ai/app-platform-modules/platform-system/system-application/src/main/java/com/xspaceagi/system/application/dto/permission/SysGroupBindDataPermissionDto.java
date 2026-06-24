package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户组绑定数据权限 DTO
 */
@Data
@Schema(description = "用户组绑定数据权限")
public class SysGroupBindDataPermissionDto implements Serializable {

    @Schema(description = "用户组ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long groupId;

    @Schema(description = "数据权限配置")
    private SysDataPermissionBindDto dataPermission;
}
