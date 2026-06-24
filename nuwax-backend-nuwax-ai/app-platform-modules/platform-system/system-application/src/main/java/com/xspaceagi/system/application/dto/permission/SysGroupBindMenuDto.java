package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema(description = "用户组绑定菜单")
@Data
public class SysGroupBindMenuDto implements Serializable {

    @Schema(description = "用户组ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long groupId;

    @Schema(description = "菜单树", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<MenuNodeDto> menuTree;

    /**
     * 获取所有菜单节点（扁平化列表，包含菜单ID和资源绑定信息）
     */
    @Schema(hidden = true)
    public List<MenuNodeDto> getAllMenuNodes() {
        return MenuNodeDto.flattenMenuTree(menuTree);
    }
}
