package com.xspaceagi.system.domain.service;

import com.xspaceagi.system.domain.model.SortIndex;
import com.xspaceagi.system.infra.dao.entity.SysResource;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.Collection;
import java.util.List;

/**
 * 系统资源领域服务
 */
public interface SysResourceDomainService {

    /**
     * 添加角色
     */
    void addResource(SysResource resource, UserContext userContext);

    /**
     * 更新角色
     */
    void updateResource(SysResource resource, UserContext userContext);

    /**
     * 删除角色
     */
    void deleteResource(Long resourceId, UserContext userContext);

    /**
     * 根据ID查询角色
     */
    SysResource queryResourceById(Long resourceId);

    /**
     * 根据编码查询角色
     */
    SysResource queryResourceByCode(String resourceCode);

    /**
     * 查询角色列表
     */
    List<SysResource> queryResourceList(SysResource resource);

    /**
     * 按ID批量查询资源
     */
    List<SysResource> queryResourceByIds(Collection<Long> ids);

    /**
     * 批量调整资源顺序，支持修改 parentId 和 sortIndex
     */
    boolean batchUpdateResourceSort(List<SortIndex> sortIndexList, UserContext userContext);
}