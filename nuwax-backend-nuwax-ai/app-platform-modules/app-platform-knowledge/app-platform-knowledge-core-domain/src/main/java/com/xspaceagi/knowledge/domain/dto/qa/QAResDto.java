package com.xspaceagi.knowledge.domain.dto.qa;

import com.xspaceagi.knowledge.sdk.response.KnowledgeQaVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "问答结果")
public class QAResDto implements Serializable {

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


    public static KnowledgeQaVo convertToVo(QAResDto qaResDto) {
        KnowledgeQaVo knowledgeQaVo = new KnowledgeQaVo();
        knowledgeQaVo.setQaId(qaResDto.getQaId());
        knowledgeQaVo.setKbId(qaResDto.getKbId());
        knowledgeQaVo.setDocId(qaResDto.getDocId());
        knowledgeQaVo.setQuestion(qaResDto.getQuestion());
        knowledgeQaVo.setAnswer(qaResDto.getAnswer());
        knowledgeQaVo.setRawTxt(qaResDto.getRawTxt());
        knowledgeQaVo.setScore(qaResDto.getScore());
        return knowledgeQaVo;

    }

}
