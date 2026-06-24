package com.xspaceagi.knowledge.domain.repository;

import com.xspaceagi.knowledge.domain.model.KnowledgeTaskHistoryModel;
import com.xspaceagi.knowledge.domain.model.KnowledgeTaskModel;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

public interface IKnowledgeTaskHistoryRepository {

    /**
     * 根据ID集合查询列表
     *
     * @param ids id集合
     * @return 列表
     */
    List<KnowledgeTaskHistoryModel> queryListByIds(List<Long> ids);

    /**
     * 根据主键查询 单条记录
     *
     * @param id id
     * @return 单条记录
     */
    KnowledgeTaskHistoryModel queryOneInfoById(Long id);

    /**
     * 更新
     *
     * @param model entity
     * @return id
     */
    Long updateInfo(KnowledgeTaskHistoryModel model, UserContext userContext);

    /**
     * 新增
     */
    Long addInfo(KnowledgeTaskHistoryModel model, UserContext userContext);

    /**
     * 批量归档已经成功的重试数据
     *
     * @param modelList   知识库任务
     * @param userContext 用户
     */
    void batchArchiveInfo(List<KnowledgeTaskModel> modelList, UserContext userContext);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id, UserContext userContext);
}
