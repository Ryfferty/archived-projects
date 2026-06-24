package com.xspaceagi.system.application.dto.permission.export;

import lombok.Data;

/**
 * 菜单资源关联导出DTO（code关联）
 */
@Data
public class MenuResourceExportDto {

    private String menuCode;
    private String resourceCode;
    private Integer resourceBindType;
}
