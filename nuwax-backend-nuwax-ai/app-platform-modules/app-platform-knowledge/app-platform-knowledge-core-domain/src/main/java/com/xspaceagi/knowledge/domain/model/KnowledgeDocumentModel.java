package com.xspaceagi.knowledge.domain.model;

import com.xspaceagi.knowledge.sdk.enums.KnowledgeDocStatueEnum;
import com.xspaceagi.knowledge.sdk.enums.KnowledgePubStatusEnum;
import com.xspaceagi.knowledge.sdk.response.KnowledgeDocumentVo;
import com.xspaceagi.knowledge.sdk.vo.SegmentConfigModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class KnowledgeDocumentModel {
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

    @Schema(description = "知识库文档状态,1:分析中;2:分析成功;10:分析失败;")
    private KnowledgeDocStatueEnum docStatus;

    @Schema(description = "知识库文档状态原因,失败的原因")
    private String docStatusReason;

    @Schema(description = "是否已经生成Q&A")
    private Boolean hasQa;

    @Schema(description = "是否已经完成嵌入")
    private Boolean hasEmbedding;

    @Schema(description = "文档分段方式")
    private SegmentConfigModel segmentConfig;

    @Schema(description = "所属空间ID")
    private Long spaceId;

    /**
     * 自定义文本内容,自定义添加会有
     */
    @Schema(description = "文件内容")
    private String fileContent;

    /**
     * 文件大小,单位字节byte
     */
    @Schema(description = "文件大小,单位字节byte")
    private Long fileSize;

    /**
     * 文件类型,1:URL访问文件;2:自定义文本内容
     */
    @Schema(description = "文件类型,1:URL访问文件;2:自定义文本内容")
    private Integer dataType;

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

    /**
     * 租户ID
     */
    private Long tenantId;



    public static KnowledgeDocumentVo convertFromModel(KnowledgeDocumentModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        KnowledgeDocumentVo knowledgeDocumentVo = new KnowledgeDocumentVo();
        knowledgeDocumentVo.setId(model.getId());
        knowledgeDocumentVo.setKbId(model.getKbId());
        knowledgeDocumentVo.setName(model.getName());
        knowledgeDocumentVo.setDocUrl(model.getDocUrl());
        knowledgeDocumentVo.setPubStatus(model.getPubStatus());
        knowledgeDocumentVo.setDocStatus(model.getDocStatus());
        knowledgeDocumentVo.setDocStatusReason(model.getDocStatusReason());
        knowledgeDocumentVo.setHasQa(model.getHasQa());
        knowledgeDocumentVo.setHasEmbedding(model.getHasEmbedding());
        knowledgeDocumentVo.setSegmentConfig(model.getSegmentConfig());
        knowledgeDocumentVo.setSpaceId(model.getSpaceId());
        knowledgeDocumentVo.setFileContent(model.getFileContent());
        knowledgeDocumentVo.setFileSize(model.getFileSize());
        knowledgeDocumentVo.setDataType(model.getDataType());
        knowledgeDocumentVo.setCreated(model.getCreated());
        knowledgeDocumentVo.setCreatorId(model.getCreatorId());
        knowledgeDocumentVo.setCreatorName(model.getCreatorName());
        knowledgeDocumentVo.setModified(model.getModified());
        knowledgeDocumentVo.setModifiedId(model.getModifiedId());
        knowledgeDocumentVo.setModifiedName(model.getModifiedName());
        knowledgeDocumentVo.setTenantId(model.getTenantId());
        return knowledgeDocumentVo;

    }
}