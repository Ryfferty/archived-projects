package com.xspaceagi.knowledge.domain.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 大模型生成的问答
 */
@Getter
@Setter
public class KnowledgeQaForModelListVo {


    @JsonPropertyDescription("多个问答列表")
    private List<KnowledgeQaForModelResponseVo> qaList;

}
