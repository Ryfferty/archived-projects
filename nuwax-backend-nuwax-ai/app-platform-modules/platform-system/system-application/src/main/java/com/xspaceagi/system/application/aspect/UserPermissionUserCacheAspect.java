package com.xspaceagi.system.application.aspect;

import com.xspaceagi.system.application.service.SysUserPermissionCacheService;
import com.xspaceagi.system.spec.annotation.ClearUserPermissionCacheByUserIds;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户维度权限缓存清理切面
 */
@Slf4j
@Aspect
@Component
public class UserPermissionUserCacheAspect {

    @Resource
    private SysUserPermissionCacheService sysUserPermissionCacheService;

    @AfterReturning("@annotation(clearAnno)")
    public void clearCacheByUserIds(JoinPoint joinPoint, ClearUserPermissionCacheByUserIds clearAnno) {
        Object[] args = joinPoint.getArgs();
        int[] indexes = clearAnno.userIdParamIndexes();
        List<Long> userIds = new ArrayList<>();

        if (indexes != null) {
            for (int idx : indexes) {
                if (idx < 0 || idx >= args.length) {
                    continue;
                }
                Object arg = args[idx];
                collectUserIdsFromArg(arg, userIds);
            }
        }

        if (!userIds.isEmpty()) {
            try {
                sysUserPermissionCacheService.clearCacheByUserIds(userIds);
            } catch (Exception e) {
                log.warn("按用户ID清理权限缓存失败, userIds={}", userIds, e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void collectUserIdsFromArg(Object arg, List<Long> userIds) {
        if (arg == null) {
            return;
        }
        if (arg instanceof Long) {
            userIds.add((Long) arg);
        } else if (arg instanceof Collection<?>) {
            for (Object item : (Collection<?>) arg) {
                if (item instanceof Long) {
                    userIds.add((Long) item);
                }
            }
        }
    }
}

