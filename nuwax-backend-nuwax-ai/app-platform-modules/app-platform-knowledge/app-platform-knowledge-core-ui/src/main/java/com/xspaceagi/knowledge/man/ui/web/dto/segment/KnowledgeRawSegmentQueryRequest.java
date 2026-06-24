package com.xspaceagi.knowledge.man.ui.web.dto.segment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Schema(description = "知识库分段-新增请求参数")
@Getter
@Setter
public class KnowledgeRawSegmentQueryRequest implements Serializable {

    /**
     * 所属空间ID
     */
    @Schema(description = "所属空间ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long spaceId;


    @Schema(description = "分段所属文档", requiredMode = Schema.RequiredMode.REQUIRED)
    private String docId;


}
