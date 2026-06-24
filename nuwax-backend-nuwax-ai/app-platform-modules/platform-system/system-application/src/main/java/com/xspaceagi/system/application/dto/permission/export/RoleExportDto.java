package com.xspaceagi.system.application.dto.permission.export;

import lombok.Data;

/**
 * 角色导出DTO（code关联，无id）
 */
@Data
public class RoleExportDto {

    private String code;
    private String name;
    private String description;
    private Integer source;
    private Integer status;
    private Integer sortIndex;
}
