package com.xspaceagi.system.domain.service;

import com.xspaceagi.system.domain.model.MenuNode;
import com.xspaceagi.system.domain.model.SortIndex;
import com.xspaceagi.system.infra.dao.entity.SysMenu;
import com.xspaceagi.system.infra.dao.entity.SysMenuResource;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.Collection;
import java.util.List;

/**
 * 菜单领域服务
 */
public interface SysMenuDomainService {

    /**
     * 新增菜单
     */
    void addMenu(SysMenu menu, MenuNode menuNode, Integer source, UserContext userContext);

    /**
     * 更新菜单
     */
    void updateMenu(SysMenu menu, MenuNode menuNode, Integer source, UserContext userContext);

    /**
     * 删除菜单
     */
    void deleteMenu(Long menuId, UserContext userContext);

    /**
     * 根据ID查询菜单
     */
    SysMenu queryMenuById(Long menuId);

    /**
     * 根据编码查询菜单
     */
    SysMenu queryMenuByCode(String code);

    /**
     * 查询菜单列表
     */
    List<SysMenu> queryMenuList(SysMenu menu);

    /**
     * 按ID批量查询菜单
     */
    List<SysMenu> queryMenuByIds(Collection<Long> ids);

    /**
     * 菜单绑定资源
     */
    void bindResource(MenuNode model, UserContext userContext);

    /**
     * 查询菜单资源关联
     */
    List<SysMenuResource> queryResourceListByMenuId(Long menuId);

    /**
     * 按多个菜单ID批量查询菜单资源关联（用于避免 N+1 查询）
     *
     * @param menuIds 菜单ID集合，可为空
     * @return 所有关联记录，按 menuId 分组由调用方处理
     */
    List<SysMenuResource> queryResourceListByMenuIds(Collection<Long> menuIds);

    /**
     * 批量调整菜单顺序，支持修改 parentId 和 sortIndex
     */
    boolean batchUpdateMenuSort(List<SortIndex> sortIndexList, UserContext userContext);
}


