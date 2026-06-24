package com.xspaceagi.system.application.dto.permission;

import com.xspaceagi.system.spec.enums.OpenTypeEnum;
import com.xspaceagi.system.spec.enums.SourceEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysMenuQueryDto implements Serializable {

    @Schema(description = "父级ID")
    private Long parentId;

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
     * @see OpenTypeEnum
     */
    @Schema(description = "打开方式 1-当前标签页打开 2-新标签页打开")
    private Integer openType;

    @Schema(description = "状态 1:启用 0:禁用")
    private Integer status;

}