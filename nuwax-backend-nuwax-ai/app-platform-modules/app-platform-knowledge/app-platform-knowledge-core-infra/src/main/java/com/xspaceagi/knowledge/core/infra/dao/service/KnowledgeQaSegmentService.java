package com.xspaceagi.knowledge.core.infra.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeQaSegment;

import java.util.List;

/**
 * @author soddy
 * @description 针对表【knowledge_qa_segment(问答表)】的数据库操作Service
 * @createDate 2025-01-21 15:33:38
 */
public interface KnowledgeQaSegmentService extends IService<KnowledgeQaSegment> {

    /**
     * 根据ID集合查询列表
     *
     * @param ids id集合
     * @return 列表
     */
    List<KnowledgeQaSegment> queryListByIds(List<Long> ids);

    /**
     * 根据文档id查询列表
     *
     * @param docIds 文档id列表
     * @return 列表
     */
    List<KnowledgeQaSegment> queryListByDocIds(List<Long> docIds);

    /**
     * 根据文档id查询没有嵌入的问答
     *
     * @param docId 文档id
     * @return 没有嵌入的问答
     */
    List<KnowledgeQaSegment> queryListByDocIdAndNoEmbedding(Long docId);

    /**
     * 根据文档id查询没有嵌入的问答总数,用于判断是否全部向量化完毕
     * 
     * @param docId
     * @return
     */
    Long queryCountByDocIdAndNoEmbedding(Long docId);

    /**
     * 根据主键查询 单条记录
     * 
     * @param id id
     * @return 单条记录
     */
    KnowledgeQaSegment queryOneInfoById(Long id);

    /**
     * 更新
     *
     * @param entity entity
     * @return id
     */
    Long updateInfo(KnowledgeQaSegment entity);

    /**
     * 新增
     */
    Long addInfo(KnowledgeQaSegment entity);

    /**
     * 批量新增
     *
     * @param list
     * @return
     */
    void batchAddInfo(List<KnowledgeQaSegment> list);

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
    void deleteByConfigId(Long kbId);

    /**
     * 根据文档的id,进行批量删除
     * 
     * @param docId 文档id
     */
    void deleteByConfigDocumentId(Long docId);

    /**
     * 根据rawId删除
     *
     * @param rawId rawId
     */
    void deleteByRawId(Long rawId);

    /**
     * 根据rawId查询问答id列表
     * 
     * @param rawId rawId
     * @return 问答id列表
     */
    List<Long> queryQaIdList(Long rawId);

    /**
     * 根据rawId列表查询没有嵌入的问答
     *
     * @param rawIdList rawId列表
     * @return 没有嵌入的问答
     */
    List<KnowledgeQaSegment> queryListByRawIdsAndNoEmbedding(List<Long> rawIdList);

    /**
     * 根据rawId查询问答列表
     * 
     * @param rawId rawId
     * @return 问答列表
     */
    List<KnowledgeQaSegment> queryListInfoByRawId(Long rawId);
}
