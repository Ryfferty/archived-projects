package com.xspaceagi.system.application.dto.permission.export;

import lombok.Data;

import java.util.List;

/**
 * 用户组菜单关联导出DTO（code关联）
 */
@Data
public class GroupMenuExportDto {

    private String groupCode;
    private String menuCode;
    private Integer menuBindType;

    /**
     * 资源树（code版），用于导入时根据目标租户的resource code->id 映射重写
     */
    private List<ResourceNodeExportDto> resourceTree;
}
