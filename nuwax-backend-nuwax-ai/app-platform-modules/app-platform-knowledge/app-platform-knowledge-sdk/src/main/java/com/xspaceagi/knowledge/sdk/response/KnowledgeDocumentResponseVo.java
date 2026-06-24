package com.xspaceagi.knowledge.sdk.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class KnowledgeDocumentResponseVo implements Serializable {

    @Schema(description = "文档列表结果")
    private List<KnowledgeDocumentVo> documentVoList;

}
