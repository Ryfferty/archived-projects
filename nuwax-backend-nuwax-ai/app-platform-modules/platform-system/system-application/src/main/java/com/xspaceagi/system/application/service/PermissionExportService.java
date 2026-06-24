package com.xspaceagi.system.application.service;

import com.xspaceagi.system.application.dto.permission.export.PermissionExportDto;

/**
 * 权限数据导出服务
 */
public interface PermissionExportService {

    /**
     * 从主租户导出内置权限数据
     */
    PermissionExportDto exportConfig(String version);
}
