package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色绑定数据权限 DTO
 */
@Data
@Schema(description = "角色绑定数据权限")
public class SysRoleBindDataPermissionDto implements Serializable {

    @Schema(description = "角色ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long roleId;

    @Schema(description = "数据权限配置")
    private SysDataPermissionBindDto dataPermission;
}
