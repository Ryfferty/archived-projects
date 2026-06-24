package com.xspaceagi.knowledge.man.ui.web.dto.document;

import com.xspaceagi.knowledge.sdk.enums.KnowledgeDocStatueEnum;
import com.xspaceagi.knowledge.sdk.enums.KnowledgePubStatusEnum;
import com.xspaceagi.knowledge.sdk.vo.SegmentConfigModel;
import com.xspaceagi.knowledge.domain.model.KnowledgeDocumentModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class KnowledgeDocumentVo {
    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "文档所属知识库")
    private Long kbId;

    @Schema(description = "文档名称")
    private String name;

    @Schema(description = "文件URL")
    private String docUrl;

    @Schema(description = "发布状态")
    private KnowledgePubStatusEnum pubStatus;

    @Schema(description = "是否已经生成Q&A")
    private Boolean hasQa;

    @Schema(description = "是否已经完成嵌入")
    private Boolean hasEmbedding;

    @Schema(description = "文档分段方式")
    private SegmentConfigModel segment;

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

    @Schema(description = "知识库文档状态,1:分析中;2:分析成功;10:分析失败;")
    private KnowledgeDocStatueEnum docStatus;

    @Schema(description = "知识库文档状态")
    private Integer docStatusCode;

    @Schema(description = "知识库文档状态描述")
    private String docStatusDesc;

    @Schema(description = "知识库文档状态原因")
    private String docStatusReason;

    public static KnowledgeDocumentVo convert2Dto(KnowledgeDocumentModel knowledgeDocumentModel) {
        if(Objects.isNull(knowledgeDocumentModel)) {
            return null;
        }
        KnowledgeDocumentVo knowledgeDocumentVo = new KnowledgeDocumentVo();
        knowledgeDocumentVo.setId(knowledgeDocumentModel.getId());
        knowledgeDocumentVo.setKbId(knowledgeDocumentModel.getKbId());
        knowledgeDocumentVo.setName(knowledgeDocumentModel.getName());
        knowledgeDocumentVo.setDocUrl(knowledgeDocumentModel.getDocUrl());
        knowledgeDocumentVo.setPubStatus(knowledgeDocumentModel.getPubStatus());
        knowledgeDocumentVo.setHasQa(knowledgeDocumentModel.getHasQa());
        knowledgeDocumentVo.setHasEmbedding(knowledgeDocumentModel.getHasEmbedding());
        knowledgeDocumentVo.setSegment(knowledgeDocumentModel.getSegmentConfig());
        knowledgeDocumentVo.setSpaceId(knowledgeDocumentModel.getSpaceId());
        knowledgeDocumentVo.setCreated(knowledgeDocumentModel.getCreated());
        knowledgeDocumentVo.setCreatorId(knowledgeDocumentModel.getCreatorId());
        knowledgeDocumentVo.setCreatorName(knowledgeDocumentModel.getCreatorName());
        knowledgeDocumentVo.setModified(knowledgeDocumentModel.getModified());
        knowledgeDocumentVo.setModifiedId(knowledgeDocumentModel.getModifiedId());
        knowledgeDocumentVo.setModifiedName(knowledgeDocumentModel.getModifiedName());

        var docStatus = knowledgeDocumentModel.getDocStatus();
        knowledgeDocumentVo.setDocStatus(docStatus);
        if (Objects.nonNull(docStatus)) {
            knowledgeDocumentVo.setDocStatusCode(docStatus.getCode());
            knowledgeDocumentVo.setDocStatusDesc(docStatus.getDesc());
        }
        knowledgeDocumentVo.setDocStatusReason(knowledgeDocumentModel.getDocStatusReason());

        return knowledgeDocumentVo;

    }
}