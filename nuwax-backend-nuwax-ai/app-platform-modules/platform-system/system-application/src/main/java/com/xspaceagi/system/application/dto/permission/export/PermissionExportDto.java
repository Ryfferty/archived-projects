package com.xspaceagi.system.application.dto.permission.export;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限数据导出根DTO（版本化管理，随代码发布）
 */
@Data
public class PermissionExportDto {

    /**
     * 版本号，与app.version对应，如 1.0.5
     */
    private String version;

    private List<ResourceExportDto> resources = new ArrayList<>();
    private List<MenuExportDto> menus = new ArrayList<>();
    private List<RoleExportDto> roles = new ArrayList<>();
    private List<GroupExportDto> groups = new ArrayList<>();
    private List<MenuResourceExportDto> menuResources = new ArrayList<>();
    private List<RoleMenuExportDto> roleMenus = new ArrayList<>();
    private List<GroupMenuExportDto> groupMenus = new ArrayList<>();
    private List<DataPermissionExportDto> dataPermissions = new ArrayList<>();
}
