package com.xspaceagi.system.application.service.impl;

import com.xspaceagi.system.application.service.SysMenuApplicationService;
import com.xspaceagi.system.application.service.SysUserPermissionCacheService;
import com.xspaceagi.system.domain.model.MenuNode;
import com.xspaceagi.system.domain.model.SortIndex;
import com.xspaceagi.system.domain.service.SysMenuDomainService;
import com.xspaceagi.system.infra.dao.entity.SysMenu;
import com.xspaceagi.system.infra.dao.entity.SysMenuResource;
import com.xspaceagi.system.spec.annotation.ClearAllUserPermissionCache;
import com.xspaceagi.system.spec.common.UserContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 菜单应用服务实现
 */
@Service
public class SysMenuApplicationServiceImpl implements SysMenuApplicationService {

    @Resource
    private SysMenuDomainService sysMenuDomainService;
    @Resource
    private SysUserPermissionCacheService sysUserPermissionCacheService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ClearAllUserPermissionCache
    public void addMenu(SysMenu menu, MenuNode menuNode, Integer source, UserContext userContext) {
        sysMenuDomainService.addMenu(menu, menuNode, source, userContext);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ClearAllUserPermissionCache
    public void updateMenu(SysMenu menu, MenuNode menuNode, Integer source, UserContext userContext) {
        sysMenuDomainService.updateMenu(menu, menuNode, source, userContext);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ClearAllUserPermissionCache
    public void deleteMenu(Long menuId, UserContext userContext) {
        sysMenuDomainService.deleteMenu(menuId, userContext);
    }

    @Override
    public SysMenu getMenuById(Long menuId) {
        return sysMenuDomainService.queryMenuById(menuId);
    }

    @Override
    public SysMenu getMenuByCode(String code) {
        return sysMenuDomainService.queryMenuByCode(code);
    }

    @Override
    public List<SysMenu> getMenuList(SysMenu menu) {
        return sysMenuDomainService.queryMenuList(menu);
    }

    @Override
    public List<SysMenu> getMenuByIds(Collection<Long> ids) {
        return sysMenuDomainService.queryMenuByIds(ids);
    }

    @Override
    public List<SysMenuResource> getResourceListByMenuId(Long menuId) {
        return sysMenuDomainService.queryResourceListByMenuId(menuId);
    }

    @ClearAllUserPermissionCache
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindResource(MenuNode model, UserContext userContext) {
        sysMenuDomainService.bindResource(model, userContext);
    }

    @ClearAllUserPermissionCache
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateMenuSort(List<SortIndex> sortIndexList, UserContext userContext) {
        boolean hasUpdateParent = sysMenuDomainService.batchUpdateMenuSort(sortIndexList, userContext);
        // 仅当修改了 parentId（菜单层级变化）时才清除权限缓存
        if (hasUpdateParent) {
            sysUserPermissionCacheService.clearCacheAll();
        }
    }
}