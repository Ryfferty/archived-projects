package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema(description = "用户绑定用户组")
@Data
public class SysUserBindGroupDto implements Serializable {

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "组ID")
    private List<Long> groupIds;


}