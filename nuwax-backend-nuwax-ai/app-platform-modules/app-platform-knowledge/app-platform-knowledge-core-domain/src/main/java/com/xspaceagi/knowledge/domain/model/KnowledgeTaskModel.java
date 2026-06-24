package com.xspaceagi.knowledge.domain.model;

import com.xspaceagi.knowledge.core.spec.enums.KnowledgeTaskRunTypeEnum;
import com.xspaceagi.knowledge.core.spec.enums.KnowledgeTaskStageStatusEnum;
import com.xspaceagi.system.spec.common.UserContext;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 知识库-定时任务
 *
 * @TableName knowledge_task
 */
@Data
public class KnowledgeTaskModel {
    /**
     * 主键id
     */
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
     * @see KnowledgeTaskRunTypeEnum
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
     * @see KnowledgeTaskStageStatusEnum
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
     * 创建空的任务模型,还缺失空间id,知识库id 等字段
     *
     * @param docId       文档id
     * @param runType     运行类型
     * @param userContext 用户上下文
     * @return 任务模型
     */
    public static KnowledgeTaskModel createEmptyTaskModel(Long docId, KnowledgeTaskRunTypeEnum runType, UserContext userContext) {
        KnowledgeTaskModel knowledgeTaskModel = new KnowledgeTaskModel();
        knowledgeTaskModel.setDocId(docId);
        knowledgeTaskModel.setType(runType.getType());
        knowledgeTaskModel.setTid(UUID.randomUUID().toString());
        knowledgeTaskModel.setName(runType.getDesc());
        knowledgeTaskModel.setStatus(KnowledgeTaskStageStatusEnum.INIT.getStatus());
        knowledgeTaskModel.setMaxRetryCnt(5);
        knowledgeTaskModel.setRetryCnt(0);
        knowledgeTaskModel.setTenantId(userContext.getTenantId());
        knowledgeTaskModel.setCreatorId(userContext.getUserId());
        knowledgeTaskModel.setCreatorName(userContext.getUserName());
        knowledgeTaskModel.setCreated(LocalDateTime.now());
        knowledgeTaskModel.setModified(LocalDateTime.now());
        return knowledgeTaskModel;
    }

}