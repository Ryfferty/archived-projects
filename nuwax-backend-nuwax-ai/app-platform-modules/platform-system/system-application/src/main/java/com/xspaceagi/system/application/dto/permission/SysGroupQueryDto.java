package com.xspaceagi.system.application.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysGroupQueryDto implements Serializable {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "来源,1:系统内置 2:用户自定义")
    private Integer source;

    @Schema(description = "状态,1:启用 0:禁用")
    private Integer status;

}