package com.xspaceagi.system.web.dto;

import com.xspaceagi.system.infra.dao.entity.SpaceUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class SpaceUserRoleUpdateDto implements Serializable {

    @Schema(description = "空间ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long spaceId;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;

    @Schema(description = "用户角色", requiredMode = Schema.RequiredMode.REQUIRED)
    private SpaceUser.Role role;
}
