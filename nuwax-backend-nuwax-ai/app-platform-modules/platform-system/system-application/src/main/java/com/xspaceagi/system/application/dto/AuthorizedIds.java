package com.xspaceagi.system.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 用户有权限的菜单ID与资源ID集合（用于按需加载，避免全量查询）
 */
@Data
@AllArgsConstructor
public class AuthorizedIds {

    private final Set<Long> menuIds;
    private final Set<Long> resourceIds;

}
