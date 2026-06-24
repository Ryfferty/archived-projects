package com.xspaceagi.knowledge.core.infra.dao.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 原始分段（也称chunk）表，这些信息待生成问答后可以不再保存
 *
 * @TableName knowledge_raw_segment
 */
@TableName(value = "knowledge_raw_segment")
@Getter
@Setter
public class KnowledgeRawSegment {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分段所属文档
     */
    private Long docId;

    /**
     * 原始文本
     */
    private String rawTxt;

    /**
     * 知识库ID
     */
    private Long kbId;

    /**
     * 排序索引,在归属同一个文档下，段的排序
     */
    private Integer sortIndex;

    /**
     * 租户ID
     */
    @TableField(value = "_tenant_id")
    private Long tenantId;

    /**
     * 所属空间ID
     */
    private Long spaceId;

    @Schema(description = "问答状态,-1:待生成问答;1:已生成问答;")
    private Integer qaStatus;

    /**
     * 全文检索同步状态: 0-未同步, 1-已同步
     */
    private Integer fulltextSyncStatus;

    /**
     * 全文检索同步时间
     */
    private LocalDateTime fulltextSyncTime;

    /**
     * 创建时间
     */
    private LocalDateTime created;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 更新时间
     */
    private LocalDateTime modified;

    /**
     * 最后修改人id
     */
    private Long modifiedId;

    /**
     * 最后修改人
     */
    private String modifiedName;

    /**
     * 逻辑标记,1:有效;-1:无效
     */
    private Integer yn;
}