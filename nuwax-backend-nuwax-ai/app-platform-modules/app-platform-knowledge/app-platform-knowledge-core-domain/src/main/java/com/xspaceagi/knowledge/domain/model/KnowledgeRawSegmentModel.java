package com.xspaceagi.knowledge.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 原始分段（也称chunk）表，这些信息待生成问答后可以不再保存
 * 
 * @TableName knowledge_raw_segment
 */
@Data
public class KnowledgeRawSegmentModel {
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


    /**
     * @see QaStatusEnum
     */
    @Schema(description = "问答状态,-1:待生成问答;1:已生成问答;")
    private Integer qaStatus;

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

    @Schema(description = "全文检索同步状态: 0-未同步, 1-已同步")
    private Integer fulltextSyncStatus;

    @Schema(description = "全文检索同步时间")
    private LocalDateTime fulltextSyncTime;
}