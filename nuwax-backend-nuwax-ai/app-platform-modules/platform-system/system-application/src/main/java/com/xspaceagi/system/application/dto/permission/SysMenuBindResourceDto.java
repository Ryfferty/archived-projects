package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单绑定资源请求
 */
@Schema(description = "菜单绑定资源")
@Data
public class SysMenuBindResourceDto implements Serializable {

    @Schema(description = "菜单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long menuId;

    @Schema(description = "资源树")
    private List<ResourceNodeDto> resourceTree;

    /**
     * 获取扁平化资源列表
     */
    @Schema(hidden = true)
    public List<ResourceNodeDto> getFlattenlResourceList() {
        return ResourceNodeDto.flattenResourceTree(resourceTree);
    }
}