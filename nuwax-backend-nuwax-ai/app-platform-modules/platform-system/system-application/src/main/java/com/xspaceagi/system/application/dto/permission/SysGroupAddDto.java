package com.xspaceagi.system.application.dto.permission;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysGroupAddDto implements Serializable {

    @Schema(description = "编码（可选，如果不传则根据名称自动生成）")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "最大用户数")
    private Integer maxUserCount;

    @Schema(description = "来源,1:系统内置 2:用户自定义", hidden = true)
    private Integer source;

    @Schema(description = "状态,1:启用 0:禁用")
    private Integer status;

    @Schema(description = "排序")
    private Integer sortIndex;
}