package com.xspaceagi.knowledge.core.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 问答表
 * @TableName knowledge_qa_segment
 */
@TableName(value ="knowledge_qa_segment")
@Getter
@Setter
public class KnowledgeQaSegment {
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
     * 所属原始分段ID
     */
    private Long rawId;

    /**
     * 问题会进行嵌入（对分段的增删改会走大模型并调用向量数据库）
     */
    private String question;

    /**
     * 答案会进行嵌入（对分段的增删改会走大模型并调用向量数据库）
     */
    private String answer;

    /**
     * 知识库ID
     */
    private Long kbId;

    /**
     * 是否已经完成嵌入
     */
    private Boolean hasEmbedding;

    /**
     * 租户ID
     */
    @TableField(value = "_tenant_id")
    private Long tenantId;

    /**
     * 所属空间ID
     */
    private Long spaceId;

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