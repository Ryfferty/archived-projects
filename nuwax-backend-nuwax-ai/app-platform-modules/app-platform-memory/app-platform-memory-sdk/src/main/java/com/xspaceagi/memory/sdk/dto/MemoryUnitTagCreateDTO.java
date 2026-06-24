package com.xspaceagi.memory.sdk.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 记忆单元标签创建DTO
 */
@Data
@ApiModel(value = "记忆单元标签创建DTO", description = "创建记忆单元标签")
public class MemoryUnitTagCreateDTO {

    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;

    @ApiModelProperty(value = "记忆ID", required = true)
    private Long memoryId;

    @ApiModelProperty(value = "标签名称", required = true)
    private String tagName;
}
