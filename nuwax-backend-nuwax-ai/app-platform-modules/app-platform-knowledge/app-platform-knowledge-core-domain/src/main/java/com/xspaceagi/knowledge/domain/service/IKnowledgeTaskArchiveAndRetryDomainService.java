package com.xspaceagi.knowledge.domain.service;

/**
 * 知识库任务归档,以及重试
 */
public interface IKnowledgeTaskArchiveAndRetryDomainService {


    /**
     * 自动失败重试任务
     *
     * @param days 重试最近几天的失败任务
     */
    void autoRunTask(Integer days);

    /**
     * 自动QA任务,近检查问答有无向量化,无对应的文档id,分段id,是用户手动添加的问答
     *
     * @param days 重试最近几天的失败任务
     */
    void autoQaRunTask(Integer days);

    /**
     * 自动归档闭环且成功的任务
     *
     * @param days 归档xx天以前的闭环且成功的任务
     */
    void archiveTask(Integer days);
}
