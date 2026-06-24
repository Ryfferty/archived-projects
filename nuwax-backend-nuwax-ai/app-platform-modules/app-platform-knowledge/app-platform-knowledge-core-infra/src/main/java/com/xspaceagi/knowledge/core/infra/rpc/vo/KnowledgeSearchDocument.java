package com.xspaceagi.knowledge.core.infra.rpc.vo;

import com.xspaceagi.log.sdk.annotation.SearchField;
import com.xspaceagi.log.sdk.annotation.SearchIndex;
import com.xspaceagi.log.sdk.vo.SearchDocument;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SearchIndex(indexName = "knowledge")
public class KnowledgeSearchDocument extends SearchDocument {

    private Long tenantId;

    @Schema(description = "文档分段ID")
    private String id;

    @Schema(description = "文档ID")
    private Long docId;

    @Schema(description = "知识库ID")
    private Long kbId;

    @Schema(description = "原始分段文本（匹配的内容）")
    @SearchField(store = true)
    private String rawText;

    @Schema(description = "文档名称（扩展字段）")
    @SearchField(store = true)
    private String documentName;
}
