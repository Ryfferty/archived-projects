package com.xspaceagi.memory.app.service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class MemoryUnitMergeInfo {

    @JsonPropertyDescription("记忆ID，用于更新")
    private Long memoryId;
    @JsonPropertyDescription("一级分类")
    private String category;
    @JsonPropertyDescription("二级分类")
    private String subCategory;
    @JsonPropertyDescription("记忆中的键值对")
    private Map<String, String> keyValues;
    @JsonPropertyDescription("创建时间")
    private Date created;
}
