package com.xspaceagi.system.domain.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户组菜单绑定领域模型
 */
@Data
public class GroupBindMenuModel implements Serializable {

    /**
     * 用户组ID
     */
    private Long groupId;

    /**
     * 菜单绑定资源列表
     */
    private List<MenuNode> menuBindResourceList;
}

