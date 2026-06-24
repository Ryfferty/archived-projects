package com.xspaceagi.memory.sdk.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 记忆单元更新DTO
 */
@Data
public class MemoryUnitUpdateDTO {

    private Long id;
    private Long userId;
    @ApiModelProperty(value = "一级分类")
    private String category;

    @ApiModelProperty(value = "二级分类")
    private String subCategory;

    @ApiModelProperty(value = "内容JSON")
    private String contentJson;

    @ApiModelProperty(value = "是否敏感信息")
    private Boolean isSensitive;

    @ApiModelProperty(value = "状态")
    private String status;

    private List<String> tags;
}
