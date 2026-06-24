package com.xspaceagi.system.application.service.impl;

import com.xspaceagi.system.application.service.SysResourceApplicationService;
import com.xspaceagi.system.application.service.SysUserPermissionCacheService;
import com.xspaceagi.system.domain.model.SortIndex;
import com.xspaceagi.system.domain.service.SysResourceDomainService;
import com.xspaceagi.system.infra.dao.entity.SysResource;
import com.xspaceagi.system.spec.annotation.ClearAllUserPermissionCache;
import com.xspaceagi.system.spec.common.UserContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 系统资源应用服务实现
 */
@Service
public class SysResourceApplicationServiceImpl implements SysResourceApplicationService {
    
    @Resource
    private SysResourceDomainService sysResourceDomainService;
    @Resource
    private SysUserPermissionCacheService sysUserPermissionCacheService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ClearAllUserPermissionCache
    public void addResource(SysResource resource, UserContext userContext) {
        sysResourceDomainService.addResource(resource, userContext);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ClearAllUserPermissionCache
    public void updateResource(SysResource resource, UserContext userContext) {
        sysResourceDomainService.updateResource(resource, userContext);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @ClearAllUserPermissionCache
    public void deleteResource(Long resourceId, UserContext userContext) {
        sysResourceDomainService.deleteResource(resourceId, userContext);
    }

    @Override
    public SysResource getResourceById(Long resourceId) {
        return sysResourceDomainService.queryResourceById(resourceId);
    }

    @Override
    public SysResource getResourceByCode(String resourceCode) {
        return sysResourceDomainService.queryResourceByCode(resourceCode);
    }

    @Override
    public List<SysResource> getResourceList(SysResource resource) {
        return sysResourceDomainService.queryResourceList(resource);
    }

    @Override
    public List<SysResource> getResourceByIds(Collection<Long> ids) {
        return sysResourceDomainService.queryResourceByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateResourceSort(List<SortIndex> sortIndexList, UserContext userContext) {
        boolean hasUpdateParent = sysResourceDomainService.batchUpdateResourceSort(sortIndexList, userContext);
        // 仅当修改了 parentId（资源层级变化）时才清除权限缓存
        if (hasUpdateParent) {
            sysUserPermissionCacheService.clearCacheAll();
        }
    }
}

