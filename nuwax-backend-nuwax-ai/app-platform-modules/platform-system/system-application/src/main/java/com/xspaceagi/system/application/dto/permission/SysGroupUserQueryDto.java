package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据用户组ID查询用户列表的查询条件
 */
@Data
@Schema(description = "用户组用户查询条件")
public class SysGroupUserQueryDto implements Serializable {

    @Schema(description = "用户组ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long groupId;

    @Schema(description = "用户名，可选，模糊查询昵称或用户名")
    private String userName;
}
