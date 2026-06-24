package com.xspaceagi.knowledge.sdk.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class KnowledgeQaResponseVo implements Serializable {

    @Schema(description = "问答结果")
    private List<KnowledgeQaVo> qaVoList;

}
