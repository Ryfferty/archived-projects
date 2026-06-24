package com.xspaceagi.knowledge.man.ui.web.dto.segment;

import com.xspaceagi.knowledge.domain.model.KnowledgeRawSegmentModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 原始分段（也称chunk）表，这些信息待生成问答后可以不再保存
 *
 * @TableName knowledge_raw_segment
 */
@Data
public class KnowledgeRawSegmentVo {
    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "分段所属文档")
    private Long docId;

    @Schema(description = "原始文本")
    private String rawTxt;

    @Schema(description = "知识库ID")
    private Long kbId;

    @Schema(description = "排序索引,在归属同一个文档下，段的排序")
    private Integer sortIndex;

    @Schema(description = "所属空间ID")
    private Long spaceId;

    @Schema(description = "创建时间")
    private LocalDateTime created;

    @Schema(description = "创建人id")
    private Long creatorId;

    @Schema(description = "创建人")
    private String creatorName;

    @Schema(description = "更新时间")
    private LocalDateTime modified;

    @Schema(description = "最后修改人id")
    private Long modifiedId;

    @Schema(description = "最后修改人")
    private String modifiedName;


    public static KnowledgeRawSegmentVo convert2Dto(KnowledgeRawSegmentModel knowledgeRawSegmentModel) {
        KnowledgeRawSegmentVo knowledgeRawSegmentVo = new KnowledgeRawSegmentVo();
        knowledgeRawSegmentVo.setId(knowledgeRawSegmentModel.getId());
        knowledgeRawSegmentVo.setDocId(knowledgeRawSegmentModel.getDocId());
        knowledgeRawSegmentVo.setRawTxt(knowledgeRawSegmentModel.getRawTxt());
        knowledgeRawSegmentVo.setKbId(knowledgeRawSegmentModel.getKbId());
        knowledgeRawSegmentVo.setSortIndex(knowledgeRawSegmentModel.getSortIndex());
        knowledgeRawSegmentVo.setSpaceId(knowledgeRawSegmentModel.getSpaceId());
        knowledgeRawSegmentVo.setCreated(knowledgeRawSegmentModel.getCreated());
        knowledgeRawSegmentVo.setCreatorId(knowledgeRawSegmentModel.getCreatorId());
        knowledgeRawSegmentVo.setCreatorName(knowledgeRawSegmentModel.getCreatorName());
        knowledgeRawSegmentVo.setModified(knowledgeRawSegmentModel.getModified());
        knowledgeRawSegmentVo.setModifiedId(knowledgeRawSegmentModel.getModifiedId());
        knowledgeRawSegmentVo.setModifiedName(knowledgeRawSegmentModel.getModifiedName());
        return knowledgeRawSegmentVo;


    }
}