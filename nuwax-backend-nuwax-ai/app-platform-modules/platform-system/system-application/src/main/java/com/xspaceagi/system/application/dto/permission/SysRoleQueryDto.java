package com.xspaceagi.system.application.dto.permission;

import com.xspaceagi.system.spec.enums.SourceEnum;
import com.xspaceagi.system.spec.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleQueryDto implements Serializable {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    /**
     * @see SourceEnum
     */
    @Schema(description = "来源 1:系统内置 2:用户自定义")
    private Integer source;

    /**
     * @see StatusEnum
     */
    @Schema(description = "状态 1:启动 0:禁用")
    private Integer status;

}