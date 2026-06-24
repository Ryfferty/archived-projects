package com.xspaceagi.system.application.service;

import com.xspaceagi.system.domain.model.SortIndex;
import com.xspaceagi.system.infra.dao.entity.SysResource;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.Collection;
import java.util.List;

/**
 * 系统资源应用服务
 */
public interface SysResourceApplicationService {

    /**
     * 添加资源
     */
    void addResource(SysResource resource, UserContext userContext);

    /**
     * 更新资源
     */
    void updateResource(SysResource resource, UserContext userContext);

    /**
     * 删除资源
     */
    void deleteResource(Long resourceId, UserContext userContext);

    /**
     * 根据ID查询资源
     */
    SysResource getResourceById(Long resourceId);

    /**
     * 根据编码查询资源
     */
    SysResource getResourceByCode(String resourceCode);

    /**
     * 查询资源列表
     */
    List<SysResource> getResourceList(SysResource resource);

    /**
     * 按ID批量查询资源（用于按有权限的资源ID加载，避免全量查询）
     */
    List<SysResource> getResourceByIds(Collection<Long> ids);

    /**
     * 批量调整资源顺序，支持修改 parentId 和 sortIndex
     */
    void batchUpdateResourceSort(List<SortIndex> sortIndexList, UserContext userContext);
}