package com.xspaceagi.knowledge.core.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识库-定时任务
 *
 * @TableName knowledge_task
 */
@TableName(value = "knowledge_task")
@Data
public class KnowledgeTask {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 文档所属知识库
     */
    private Long kbId;

    /**
     * 所属空间ID
     */
    private Long spaceId;

    /**
     * 文档id
     */
    private Long docId;

    /**
     * 任务重试阶段类型:1:文档分段;2:生成Q&A;3:生成嵌入;10:任务完毕
     */
    private Integer type;

    /**
     * tid
     */
    private String tid;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 状态，0:初始状态,1待重试，2重试成功，3重试失败，4禁止重试
     */
    private Integer status;

    /**
     * 最大重试次数
     */
    private Integer maxRetryCnt;

    /**
     * 已重试次数
     */
    private Integer retryCnt;

    /**
     * 调用结果
     */
    private String result;

    /**
     * 租户ID
     */
    @TableField(value = "_tenant_id")
    private Long tenantId;

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
     * 逻辑标记,1:有效;-1:无效
     */
    private Integer yn;
}