package com.xspaceagi.agent.core.sdk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Schema(description = "问答结果")
public class KnowledgeQaDto implements Serializable {

    @Schema(description = "问答ID")
    private Long qaId;

    @Schema(description = "所在知识库ID")
    private Long kbId;

    @Schema(description = "所属文档ID")
    private Long docId;

    @Schema(description = "分段问题")
    private String question;

    @Schema(description = "分段答案")
    private String answer;

    @Schema(description = "归属分段对应的原始分段文本,可能没有", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String rawTxt;

    @Schema(description = "得分")
    private double score;
}