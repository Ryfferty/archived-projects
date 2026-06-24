package com.xspaceagi.system.application.service;

import com.xspaceagi.system.domain.model.MenuNode;
import com.xspaceagi.system.domain.model.SortIndex;
import com.xspaceagi.system.infra.dao.entity.SysMenu;
import com.xspaceagi.system.infra.dao.entity.SysMenuResource;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.Collection;
import java.util.List;

/**
 * 菜单应用服务
 */
public interface SysMenuApplicationService {

    void addMenu(SysMenu menu, MenuNode menuNode, Integer source, UserContext userContext);

    void updateMenu(SysMenu menu, MenuNode menuNode, Integer source, UserContext userContext);

    void deleteMenu(Long menuId, UserContext userContext);

    SysMenu getMenuById(Long menuId);

    SysMenu getMenuByCode(String code);

    List<SysMenu> getMenuList(SysMenu menu);

    /**
     * 按ID批量查询菜单（用于按有权限的菜单ID加载，避免全量查询）
     */
    List<SysMenu> getMenuByIds(Collection<Long> ids);

    List<SysMenuResource> getResourceListByMenuId(Long menuId);

    /**
     * 菜单绑定资源
     */
    void bindResource(MenuNode model, UserContext userContext);

    /**
     * 批量调整菜单顺序，支持修改 parentId 和 sortIndex
     */
    void batchUpdateMenuSort(List<SortIndex> sortIndexList, UserContext userContext);

}