package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "用户组绑定用户（单个）")
@Data
public class SysGroupBindUserSingleDto implements Serializable {

    @Schema(description = "用户组ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long groupId;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

}
