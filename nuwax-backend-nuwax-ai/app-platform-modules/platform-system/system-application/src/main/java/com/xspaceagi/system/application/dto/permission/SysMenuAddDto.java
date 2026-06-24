package com.xspaceagi.system.application.dto.permission;

import java.io.Serializable;
import java.util.List;

import com.xspaceagi.system.spec.enums.OpenTypeEnum;
import com.xspaceagi.system.spec.enums.SourceEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysMenuAddDto implements Serializable {

    @Schema(description = "父级ID")
    private Long parentId;

    @Schema(description = "编码（可选，如果不传则根据名称自动生成）")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    /**
     * @see SourceEnum
     */
    @Schema(description = "来源 1:系统内置 2:用户自定义", hidden = true)
    private Integer source;

    @Schema(description = "访问路径")
    private String path;

    /**
     * @see OpenTypeEnum
     */
    @Schema(description = "打开方式 1-当前标签页打开 2-新标签页打开")
    private Integer openType;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序")
    private Integer sortIndex;

    @Schema(description = "状态 1:启用 0:禁用")
    private Integer status;

    @Schema(description = "资源树")
    private List<ResourceNodeDto> resourceTree;

}