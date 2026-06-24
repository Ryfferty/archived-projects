package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据角色ID查询用户列表的查询条件
 */
@Data
@Schema(description = "角色用户查询条件")
public class SysRoleUserQueryDto implements Serializable {

    @Schema(description = "角色ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long roleId;

    @Schema(description = "用户名，可选，模糊查询昵称或用户名")
    private String userName;
}
