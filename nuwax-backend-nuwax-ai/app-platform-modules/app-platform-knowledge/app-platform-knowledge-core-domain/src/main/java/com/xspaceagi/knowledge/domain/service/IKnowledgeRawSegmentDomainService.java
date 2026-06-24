package com.xspaceagi.knowledge.domain.service;

import java.util.List;

import com.xspaceagi.knowledge.domain.model.KnowledgeRawSegmentModel;
import com.xspaceagi.system.spec.common.UserContext;

public interface IKnowledgeRawSegmentDomainService  {
 
        /**
     * 模板配置详情
     *
     * @param id 模板id
     */
    KnowledgeRawSegmentModel queryOneInfoById(Long id);


    /**
     * 删除根据主键id
     *
     * @param id id
     * @param userContext 用户上下文
     */
    void deleteById(Long id, UserContext userContext);


    /**
     * 更新
     *
     * @param model 模型
     * @return id
     */
    Long updateInfo(KnowledgeRawSegmentModel model, UserContext userContext);

    /**
     * 新增
     */
    Long addInfo(KnowledgeRawSegmentModel model, UserContext userContext);

    /**
     * 查询待生成问答的分段信息,根据字段:qaStatus=-1,created在days天前
     * @param days 天数
     * @param pageSize 每页大小
     * @param pageNum 页码
     * @return
     */
    List<KnowledgeRawSegmentModel> queryListForPendingQa(Integer days, Integer pageSize, Integer pageNum);
}
