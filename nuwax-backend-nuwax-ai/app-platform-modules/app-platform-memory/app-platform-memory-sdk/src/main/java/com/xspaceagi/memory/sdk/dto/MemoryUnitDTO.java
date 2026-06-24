package com.xspaceagi.memory.sdk.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 记忆单元DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "记忆单元DTO", description = "记忆单元数据传输对象")
public class MemoryUnitDTO {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "代理ID")
    private Long agentId;

    @ApiModelProperty(value = "一级分类")
    private String category;

    @ApiModelProperty(value = "二级分类")
    private String subCategory;

    @ApiModelProperty(value = "内容JSON")
    private String contentJson;

    @ApiModelProperty(value = "是否敏感信息(0:否 1:是)")
    private Boolean isSensitive;

    @ApiModelProperty(value = "状态，active、deleted")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private Date created;

    @ApiModelProperty(value = "修改时间")
    private Date modified;
}
