package com.xspaceagi.system.application.dto.permission.export;

import lombok.Data;

import java.util.List;

/**
 * 资源树节点导出DTO（使用code替代id，用于resource_tree_json）
 */
@Data
public class ResourceNodeExportDto {

    private String code;
    private Integer resourceBindType;
    private List<ResourceNodeExportDto> children;
}
