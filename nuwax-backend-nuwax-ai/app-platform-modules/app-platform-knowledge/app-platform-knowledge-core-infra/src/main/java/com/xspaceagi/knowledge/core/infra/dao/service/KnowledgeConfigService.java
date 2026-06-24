package com.xspaceagi.knowledge.core.infra.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeConfig;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

/**
 * @author soddy
 * @description 针对表【knowledge_config(知识库表)】的数据库操作Service
 * @createDate 2025-01-21 15:33:38
 */
public interface KnowledgeConfigService extends IService<KnowledgeConfig> {

    /**
     * 根据ID集合查询列表
     *
     * @param ids id集合
     * @return 列表
     */
    List<KnowledgeConfig> queryListByIds(List<Long> ids);

    /**
     * 根据主键查询 单条记录
     * 
     * @param id id
     * @return 单条记录
     */
    KnowledgeConfig queryOneInfoById(Long id);

    /**
     * 更新
     *
     * @param entity entity
     * @return id
     */
    Long updateInfo(KnowledgeConfig entity);

    /**
     * 新增
     */
    Long addInfo(KnowledgeConfig entity);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 根据空间ID查询知识库列表
     *
     * @param spaceId
     * @return
     */
    List<KnowledgeConfig> queryListBySpaceId(Long spaceId);



    /**
     * 用户操作修改文档,更新知识库的最后修改是极爱你
     * @param id 知识库id
     * @param userContext 用户上下文
     */
    void updateLatestModifyTime(Long id, UserContext userContext);


    /**
     * 更新知识库的文件大小
     * @param kbId 知识库id
     * @param fileSize 文件大小
     */
    void updateKnowledgeConfigFileSize(Long kbId, Long fileSize);

}
