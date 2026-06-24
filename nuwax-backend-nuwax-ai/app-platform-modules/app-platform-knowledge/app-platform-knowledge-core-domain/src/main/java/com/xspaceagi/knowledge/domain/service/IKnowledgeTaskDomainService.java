package com.xspaceagi.knowledge.domain.service;

import com.xspaceagi.knowledge.core.spec.enums.KnowledgeTaskRunTypeEnum;
import com.xspaceagi.knowledge.domain.dto.task.AutoRecordTask;
import com.xspaceagi.knowledge.domain.model.KnowledgeTaskModel;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

/**
 * 知识库任务记录,以及定时处理
 */
public interface IKnowledgeTaskDomainService {

    /**
     * 根据文件id,查询对应文档的状态,如果没查到,默认成功(因为成功任务可能被归档了)
     *
     * @param docIds 文档id列表
     * @return 任务列表
     */
    List<KnowledgeTaskModel> queryListByDocIds(List<Long> docIds);

    /**
     * 根据文档id,删除任务,然后调用方会手动重新生成重试任务
     *
     * @param docIds 文档id列表
     */
    void deleteByDocIds(List<Long> docIds);

    /**
     * 根据文档id,修改任务状态,比如 任务重试阶段类型:1:文档分段;2:生成Q&A;3:生成嵌入
     *
     * @param autoRecordTask 文档id,空间id,归属知识库id
     * @param runType        状态 ,任务重试阶段类型:1:文档分段;2:生成Q&A;3:生成嵌入
     * @param userContext    用户上下文
     */
    void changeTaskStatus(AutoRecordTask autoRecordTask, KnowledgeTaskRunTypeEnum runType, UserContext userContext);

    /**
     * 创建新的任务,默认新任务状态是 分段
     *
     * @param autoRecordTask 文档id,空间id,归属知识库id
     * @param runType
     * @param userContext    用户上下文
     */
    Long createNewTask(AutoRecordTask autoRecordTask, KnowledgeTaskRunTypeEnum runType, UserContext userContext);

}
