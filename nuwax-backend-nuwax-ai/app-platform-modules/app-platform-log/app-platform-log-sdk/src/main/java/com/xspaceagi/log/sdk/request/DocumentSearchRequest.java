package com.xspaceagi.log.sdk.request;

import com.xspaceagi.log.sdk.vo.SearchDocument;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSearchRequest implements Serializable {

    @Schema(description = "搜索文档类", requiredMode = Schema.RequiredMode.REQUIRED)
    private Class<? extends SearchDocument> searchDocumentClazz;

    @Schema(description = "搜索字段", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<String> searchFields;

    @Schema(description = "搜索关键字", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String keyword;

    @Schema(description = "过滤字段，全匹配", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<Map<String, Object>> filterFieldsAndValues;

    //排序
    @Schema(description = "排序字段", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, Object> sortFieldsAndValues;

    @Schema(description = "分页起始位置", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer from;

    @Schema(description = "分页大小", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer size;
}
