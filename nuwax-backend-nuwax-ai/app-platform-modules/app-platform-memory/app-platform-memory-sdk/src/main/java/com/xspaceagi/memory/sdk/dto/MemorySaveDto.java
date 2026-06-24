package com.xspaceagi.memory.sdk.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * API Key 数据传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemorySaveDto {

    @Schema(description = "更新、新增、追加memoryId对应记忆中的键值对")
    private List<UpdateUnitWithID> updateMemoryKeyValues;

    @Schema(description = "生成新的记忆")
    private List<InsertUnit> insertMemories;

    @Data
    public static class UpdateUnitWithID {

        @Schema(description = "记忆ID")
        private Long memoryId;

        @Schema(description = "更新（替换原有内容，比如手机号变更等）、新增、追加（在原有内容后面追加新的内容，比如之前个人形象身高180，现在又获取到了新的数据为皮肤黝黑，新内容为“身高180，皮肤黝黑”）对应记忆中的键值对")
        private Map<String, String> keyValues;

        @JsonPropertyDescription("关键词标签列表")
        private List<String> tags;

        @JsonPropertyDescription("是否为敏感信息")
        private Boolean isSensitive;
    }

    @Data
    public static class InsertUnit {

        @Schema(description = "一级分类")
        private String category;

        @Schema(description = "二级分类")
        private String subCategory;

        @Schema(description = "记忆中的键值对")
        private Map<String, String> keyValues;

        @JsonPropertyDescription("关键词标签列表")
        private List<String> tags;

        @JsonPropertyDescription("是否为敏感信息")
        private Boolean isSensitive;
    }
}
