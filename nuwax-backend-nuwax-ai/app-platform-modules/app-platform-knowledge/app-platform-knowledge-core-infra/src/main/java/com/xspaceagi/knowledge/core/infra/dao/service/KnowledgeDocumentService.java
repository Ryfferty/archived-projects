package com.xspaceagi.knowledge.core.infra.dao.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeDocument;

/**
 * @author soddy
 * @description 针对表【knowledge_document(知识库-原始文档表)】的数据库操作Service
 * @createDate 2025-01-21 15:33:38
 */
public interface KnowledgeDocumentService extends IService<KnowledgeDocument> {

    /**
     * 根据ID集合查询列表
     *
     * @param ids id集合
     * @return 列表
     */
    List<KnowledgeDocument> queryListByIds(List<Long> ids);

    /**
     * 根据ID集合查询列表
     * 
     * @param configId
     * @return
     */
    List<KnowledgeDocument> queryListByConfigId(Long configId);

    /**
     * 根据主键查询 单条记录
     * 
     * @param id id
     * @return 单条记录
     */
    KnowledgeDocument queryOneInfoById(Long id);

    /**
     * 更新
     *
     * @param entity entity
     * @return id
     */
    Long updateInfo(KnowledgeDocument entity);

    /**
     * 新增
     */
    Long addInfo(KnowledgeDocument entity);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 根据知识库基础配置的id,进行批量删除
     * 
     * @param kbId 基础配置id
     */
    void deleteByConfigyId(Long kbId);

    /**
     * 查询知识库的文件大小
     * 
     * @param kbId 知识库id
     * @return 文件大小
     */
    Long queryTotalFileSize(Long kbId);

    /**
     * 根据知识库id查询文档
     * 
     * @param kbId 知识库id
     * @return 文档列表
     */
    List<KnowledgeDocument> queryDocByKbId(Long kbId);

}
