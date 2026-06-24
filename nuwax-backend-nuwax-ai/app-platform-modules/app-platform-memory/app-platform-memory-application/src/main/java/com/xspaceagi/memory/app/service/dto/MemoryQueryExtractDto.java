package com.xspaceagi.memory.app.service.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

@Data
public class MemoryQueryExtractDto {

    @JsonPropertyDescription("判断是否需要搜索记忆，如果问题不需要查询记忆，shouldSearch 设为 false")
    private Boolean shouldSearch;
    @JsonPropertyDescription("必须严格从上述一级分类中选择，不能自创")
    private List<String> categories;
    @JsonPropertyDescription("必须严格从上述二级分类中选择，不能自创")
    private List<String> subCategories;
    @JsonPropertyDescription("关键词标签列表，分词可以细粒度一点，尽量多生成有助于更精确查找")
    private List<String> tags;
    @JsonPropertyDescription("是否需要记忆的历史变更记录，如果问题不需要，requiresHistory 设为 false")
    private Boolean requiresHistory;
    @JsonPropertyDescription("查询范围，可选值为：today|yesterday|recent|long-term|all")
    private String queryType;
    @JsonPropertyDescription("时间范围（非必填），格式为：2023-01-01 00:00:00-2023-01-01 23:59:59")
    private String timeRange;
    @JsonPropertyDescription("限制返回的记忆数量，默认为 10")
    private Integer limit;
}
