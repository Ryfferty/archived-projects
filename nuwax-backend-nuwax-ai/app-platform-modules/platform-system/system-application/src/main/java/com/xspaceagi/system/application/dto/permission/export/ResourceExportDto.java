package com.xspaceagi.system.application.dto.permission.export;

import lombok.Data;

/**
 * 资源导出DTO（code关联，无id）
 */
@Data
public class ResourceExportDto {

    private String code;
    private String name;
    private String description;
    private Integer source;
    private Integer type;
    private String parentCode;
    private String path;
    private String icon;
    private Integer sortIndex;
    private Integer status;
}
