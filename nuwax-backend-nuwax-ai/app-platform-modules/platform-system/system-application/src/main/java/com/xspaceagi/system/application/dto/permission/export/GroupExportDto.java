package com.xspaceagi.system.application.dto.permission.export;

import lombok.Data;

/**
 * 用户组导出DTO（code关联，无id）
 */
@Data
public class GroupExportDto {

    private String code;
    private String name;
    private String description;
    private Integer maxUserCount;
    private Integer source;
    private Integer status;
    private Integer sortIndex;
}
