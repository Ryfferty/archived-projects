package com.xspaceagi.knowledge.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 问答表
 * 
 * @TableName knowledge_qa_segment
 */
@Data
public class KnowledgeQaSegmentModel {
    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "分段所属文档")
    private Long docId;

    @Schema(description = "所属原始分段ID")
    private Long rawId;

    @Schema(description = "问题会进行嵌入（对分段的增删改会走大模型并调用向量数据库）")
    private String question;

    @Schema(description = "答案会进行嵌入（对分段的增删改会走大模型并调用向量数据库）")
    private String answer;

    @Schema(description = "知识库ID")
    private Long kbId;

    @Schema(description = "是否已经完成嵌入")
    private Boolean hasEmbedding;

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

    /**
     * 租户ID
     */
    private Long tenantId;
}