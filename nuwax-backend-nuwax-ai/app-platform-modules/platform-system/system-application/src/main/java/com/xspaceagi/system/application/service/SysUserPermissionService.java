package com.xspaceagi.system.application.service;

import com.xspaceagi.system.application.dto.AuthorizedIds;
import com.xspaceagi.system.domain.model.MenuNode;
import com.xspaceagi.system.infra.dao.entity.User;

import java.util.List;
import java.util.Set;

/**
 * 用户权限服务
 */
public interface SysUserPermissionService {

    /**
     * 查询用户拥有的菜单及资源权限（只返回有权限的菜单节点，返回的是打平结构）
     */
    List<MenuNode> getUserMenuAndResources(User user);

    /**
     * 从已查询的菜单节点列表提取有权限的菜单ID与资源ID（避免重复查询菜单树）
     */
    AuthorizedIds getAuthorizedMenuAndResourceIdsFromNodes(List<MenuNode> authorizedMenuNodes);

}