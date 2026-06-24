package com.xspaceagi.system.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色菜单绑定领域模型
 */
@Data
public class RoleBindMenuModel implements Serializable {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单绑定资源列表
     */
    private List<MenuNode> menuBindResourceList;

}