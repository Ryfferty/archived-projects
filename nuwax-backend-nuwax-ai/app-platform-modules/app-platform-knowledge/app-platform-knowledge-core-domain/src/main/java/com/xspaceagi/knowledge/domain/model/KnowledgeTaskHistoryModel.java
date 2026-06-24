package com.xspaceagi.knowledge.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 知识库-定时任务-历史
 *
 * @TableName knowledge_task_history
 */
@Data
public class KnowledgeTaskHistoryModel {
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
     * 转换为归档任务对象
     *
     * @param model
     * @return
     */
    public static KnowledgeTaskHistoryModel convertFrom(KnowledgeTaskModel model) {
        KnowledgeTaskHistoryModel knowledgeTaskHistoryModel = new KnowledgeTaskHistoryModel();
        knowledgeTaskHistoryModel.setId(model.getId());
        knowledgeTaskHistoryModel.setKbId(model.getKbId());
        knowledgeTaskHistoryModel.setSpaceId(model.getSpaceId());
        knowledgeTaskHistoryModel.setDocId(model.getDocId());
        knowledgeTaskHistoryModel.setType(model.getType());
        knowledgeTaskHistoryModel.setTid(model.getTid());
        knowledgeTaskHistoryModel.setName(model.getName());
        knowledgeTaskHistoryModel.setStatus(model.getStatus());
        knowledgeTaskHistoryModel.setMaxRetryCnt(model.getMaxRetryCnt());
        knowledgeTaskHistoryModel.setRetryCnt(model.getRetryCnt());
        knowledgeTaskHistoryModel.setResult(model.getResult());
        knowledgeTaskHistoryModel.setTenantId(model.getTenantId());
        knowledgeTaskHistoryModel.setCreated(model.getCreated());
        knowledgeTaskHistoryModel.setCreatorId(model.getCreatorId());
        knowledgeTaskHistoryModel.setCreatorName(model.getCreatorName());
        knowledgeTaskHistoryModel.setModified(model.getModified());
        return knowledgeTaskHistoryModel;

    }

}