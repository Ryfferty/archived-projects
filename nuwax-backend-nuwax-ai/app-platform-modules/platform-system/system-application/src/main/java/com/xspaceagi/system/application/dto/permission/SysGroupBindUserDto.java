package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema(description = "用户组绑定用户")
@Data
public class SysGroupBindUserDto implements Serializable {

    @Schema(description = "组ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long groupId;

    @Schema(description = "用户ID")
    private List<Long> userIds;

}