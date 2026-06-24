package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema(description = "角色菜单绑定")
@Data
public class SysRoleBindMenuDto implements Serializable {

    @Schema(description = "角色ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long roleId;

    @Schema(description = "菜单树形结构", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<MenuNodeDto> menuTree;

    /**
     * 获取扁平化菜单列表（包含菜单ID和资源绑定信息）
     */
    @Schema(hidden = true)
    public List<MenuNodeDto> getFlattenlMenuNodes() {
        return MenuNodeDto.flattenMenuTree(menuTree);
    }
}