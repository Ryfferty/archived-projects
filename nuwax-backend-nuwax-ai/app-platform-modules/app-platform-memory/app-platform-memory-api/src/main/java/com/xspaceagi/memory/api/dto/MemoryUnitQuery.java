package com.xspaceagi.memory.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class MemoryUnitQuery {

    @Schema(description = "必须严格从上述一级分类中选择，不能自创")
    private List<String> categories;

    @Schema(description = "必须严格从上述二级分类中选择，不能自创")
    private List<String> subCategories;

    @Schema(description = "关键词标签列表，分词可以细粒度一点，尽量多生成有助于更精确查找")
    private List<String> tags;

    @Schema(description = "查询范围，可选值为：today|yesterday|recent|long-term|all")
    private String timeRangeType;

    @Schema(description = "时间范围（非必填），格式为：2023-01-01 00:00:00-2023-01-01 23:59:59")
    private String timeRange;

    @Schema(description = "限制返回的记忆数量，默认为 10")
    private Integer limit;

    @Schema(description = "是否过滤敏感信息，默认为 true")
    private boolean filterSensitive = true;
}