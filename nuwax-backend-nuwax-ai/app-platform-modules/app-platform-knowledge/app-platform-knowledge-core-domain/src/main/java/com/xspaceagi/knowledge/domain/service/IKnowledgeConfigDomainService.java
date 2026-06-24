package com.xspaceagi.knowledge.domain.service;

import com.xspaceagi.knowledge.domain.model.KnowledgeConfigModel;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

public interface IKnowledgeConfigDomainService {


    /**
     * 模板配置详情
     *
     * @param id 模板id
     */
    KnowledgeConfigModel queryOneInfoById(Long id);

    /**
     * 批量根据kbIds查询知识库配置
     * 
     * @param kbIds 知识库id列表
     * @return 知识库配置列表
     */
    List<KnowledgeConfigModel> queryListByIds(List<Long> kbIds);

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
    Long updateInfo(KnowledgeConfigModel model, UserContext userContext);

    /**
     * 新增
     */
    Long addInfo(KnowledgeConfigModel model, UserContext userContext);

    /**
     * 根据空间ID查询知识库列表
     *
     * @param spaceId
     * @return
     */
    List<KnowledgeConfigModel> queryListBySpaceId(Long spaceId);

    /**
     * 查询知识库的文件大小
     *
     * @param kbId 知识库id
     * @return 文件大小
     */
    Long queryTotalFileSize(Long kbId);

    /**
     * 统计知识库总数
     *
     * @return 知识库总数
     */
    Long countTotalKnowledge(Long userId);
}
