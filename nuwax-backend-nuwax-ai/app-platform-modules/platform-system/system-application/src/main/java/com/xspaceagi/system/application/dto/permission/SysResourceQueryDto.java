package com.xspaceagi.system.application.dto.permission;

import com.xspaceagi.system.spec.enums.ResourceTypeEnum;
import com.xspaceagi.system.spec.enums.SourceEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "资源")
@Data
public class SysResourceQueryDto {

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
     * @see ResourceTypeEnum
     */
    @Schema(description = "资源类型 1:模块 2:组件 3:页面")
    private Integer type;
    
    @Schema(description = "状态 1:启用 0:禁用")
    private Integer status;

}

