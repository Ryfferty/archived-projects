package com.xspaceagi.knowledge.core.infra.dao.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.knowledge.core.infra.dao.entity.KnowledgeRawSegment;

/**
 * @author soddy
 * @description 针对表【knowledge_raw_segment(原始分段（也称chunk）表，这些信息待生成问答后可以不再保存)】的数据库操作Service
 * @createDate 2025-01-21 15:33:38
 */
public interface KnowledgeRawSegmentService extends IService<KnowledgeRawSegment> {

    /**
     * 根据ID集合查询列表
     *
     * @param ids id集合
     * @return 列表
     */
    List<KnowledgeRawSegment> queryListByIds(List<Long> ids);

    /**
     * 根据主键查询 单条记录
     * 
     * @param id id
     * @return 单条记录
     */
    KnowledgeRawSegment queryOneInfoById(Long id);

    /**
     * 根据文档id查询分段信息
     *
     * @param docId 文档id
     */
    List<KnowledgeRawSegment> queryListByDocId(Long docId);

    /**
     * 根据文档id查询列表
     *
     * @param docIds 文档id列表
     * @return 列表
     */
    List<KnowledgeRawSegment> queryListByDocIds(List<Long> docIds);

    /**
     * 更新
     *
     * @param entity entity
     * @return id
     */
    Long updateInfo(KnowledgeRawSegment entity);

    /**
     * 新增
     */
    Long addInfo(KnowledgeRawSegment entity);

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
     * 批量更新问答状态,根据主键id更新
     *
     * @param segments 分段列表
     */
    void batchUpdateQaStatus(List<KnowledgeRawSegment> segments);

    /**
     * 查询待生成问答的分段数量,根据字段:qaStatus=-1判断
     * @param docId 文档id
     * @return
     */
    Long  queryCountForPendingQaByDocId(Long docId);

    /**
     * 根据文档id查询分段信息,根据字段:qaStatus=-1
     * @param docId 文档id
     * @return
     */
    List<KnowledgeRawSegment>  queryListForPendingQaByDocId(Long docId);


}
