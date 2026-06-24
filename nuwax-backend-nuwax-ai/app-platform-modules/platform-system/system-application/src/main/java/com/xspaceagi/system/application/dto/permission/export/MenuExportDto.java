package com.xspaceagi.system.application.dto.permission.export;

import lombok.Data;

/**
 * 菜单导出DTO（code关联，无id）
 */
@Data
public class MenuExportDto {

    private String parentCode;
    private String code;
    private String name;
    private String description;
    private Integer source;
    private String path;
    private Integer openType;
    private String icon;
    private Integer sortIndex;
    private Integer status;
}
