package com.xspaceagi.knowledge.man.ui.web.dto.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Schema(description = "知识库文档-查询请求参数")
@Getter
@Setter
public class KnowledgeDocumentQueryRequest implements Serializable {

    /**
     * 所属空间ID
     */
    @Schema(description = "所属空间ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long spaceId;

    @Schema(description = "文档名称")
    private String name;

    @Schema(description = "知识库ID")
    private Long kbId;





}
