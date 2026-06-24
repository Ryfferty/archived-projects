package com.xspaceagi.knowledge.man.ui.web.dto.segment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "删除请求参数")
public class KnowledgeRawSegmentDeleteRequest implements Serializable {


    @Schema(description = "数据ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

}
