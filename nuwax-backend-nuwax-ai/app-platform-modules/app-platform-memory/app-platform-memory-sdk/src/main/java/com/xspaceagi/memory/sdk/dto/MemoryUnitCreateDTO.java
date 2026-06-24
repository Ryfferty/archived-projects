package com.xspaceagi.memory.sdk.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 记忆单元创建DTO
 */
@Data
@ApiModel(value = "记忆单元创建DTO", description = "创建记忆单元")
public class MemoryUnitCreateDTO {
    private Long tenantId;
    private Long userId;
    private Long agentId;
    private String category;
    private String subCategory;
    private Map<String, String> keyValues;
    private List<String> tags;
    private Boolean isSensitive;
}
