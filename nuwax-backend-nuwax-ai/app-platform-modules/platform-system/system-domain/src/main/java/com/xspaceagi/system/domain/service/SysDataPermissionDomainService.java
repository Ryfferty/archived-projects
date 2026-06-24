package com.xspaceagi.system.domain.service;

import com.xspaceagi.system.infra.dao.entity.SysDataPermission;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.enums.PermissionTargetTypeEnum;

import java.util.List;

public interface SysDataPermissionDomainService {

    void add(SysDataPermission dataPermission, UserContext userContext);

    void update(Long id, SysDataPermission dataPermission, UserContext userContext);

    SysDataPermission getByTarget(PermissionTargetTypeEnum targetType, Long targetId);

    List<SysDataPermission> getByTargetList(PermissionTargetTypeEnum targetType, List<Long> targetIds);

    void deleteByTaret(PermissionTargetTypeEnum permissionTargetTypeEnum, Long roleId, UserContext userContext);
}