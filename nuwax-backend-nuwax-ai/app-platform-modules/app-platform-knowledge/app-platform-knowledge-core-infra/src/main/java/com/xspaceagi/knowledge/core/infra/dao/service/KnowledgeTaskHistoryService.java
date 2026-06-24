package com.xspaceagi.knowledge.core.infra.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeTaskHistory;

import java.util.List;

/**
 * @author soddy
 * @description 针对表【knowledge_task_history(知识库-定时任务-历史)】的数据库操作Service
 * @createDate 2025-02-21 11:02:01
 */
public interface KnowledgeTaskHistoryService extends IService<KnowledgeTaskHistory> {


    /**
     * 根据ID集合查询列表
     *
     * @param ids id集合
     * @return 列表
     */
    List<KnowledgeTaskHistory> queryListByIds(List<Long> ids);

    /**
     * 根据主键查询 单条记录
     *
     * @param id id
     * @return 单条记录
     */
    KnowledgeTaskHistory queryOneInfoById(Long id);

    /**
     * 更新
     *
     * @param entity entity
     * @return id
     */
    Long updateInfo(KnowledgeTaskHistory entity);

    /**
     * 新增
     */
    Long addInfo(KnowledgeTaskHistory entity);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);

}
