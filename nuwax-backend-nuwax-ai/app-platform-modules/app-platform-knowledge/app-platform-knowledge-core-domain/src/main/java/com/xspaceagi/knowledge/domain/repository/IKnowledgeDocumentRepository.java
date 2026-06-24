package com.xspaceagi.knowledge.domain.repository;

import com.xspaceagi.knowledge.sdk.enums.KnowledgePubStatusEnum;
import com.xspaceagi.knowledge.domain.model.KnowledgeDocumentModel;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.infra.service.IQueryViewService;

import java.util.List;

/**
 * 知识库使用的原始文档
 */
public interface IKnowledgeDocumentRepository extends IQueryViewService<KnowledgeDocumentModel> {

    /**
     * 模板配置详情
     *
     * @param id 模板id
     */
    KnowledgeDocumentModel queryOneInfoById(Long id);

    /**
     * 根据qaSegmentId查询文档详情
     *
     * @param qaSegmentId qaSegmentId
     * @return
     */
    KnowledgeDocumentModel queryOneInfoByQaSegmentId(Long qaSegmentId);

    /**
     * 根据配置id查询文档列表,这里对应字段是:kbId
     *
     * @param configId 配置id,kbId
     * @return
     */
    List<KnowledgeDocumentModel> queryListByConfigId(Long configId);

    /**
     * 根据知识库id,查询下面所有的 文件id列表
     *
     * @param configId               知识库id
     * @param knowledgePubStatusEnum 发布状态
     * @return
     */
    List<Long> queryDocIdsByConfigId(Long configId, KnowledgePubStatusEnum knowledgePubStatusEnum);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 触发异步更新知识库文件预估大小值
     *
     * @param kbId 知识库ID
     */
    public void triggerUpdateKnowledgeFileSize(Long kbId);
    /**
     * 更新
     *
     * @param model 模型
     * @return id
     */
    Long updateInfo(KnowledgeDocumentModel model, UserContext userContext);

    /**
     * 更改文件名称
     *
     * @param model       model
     * @param userContext 用户上下文
     * @return
     */
    Long updateDocName(KnowledgeDocumentModel model, UserContext userContext);


    /**
     * 新增
     */
    Long addInfo(KnowledgeDocumentModel model, UserContext userContext);


    /**
     * 修改文档对应的qa处理处理状态
     *
     * @param docId       文档id
     * @param hasQa       Qa处理状态
     * @param userContext 用户上下文
     */
    void changeHasQaStatus(Long docId, Boolean hasQa, UserContext userContext);

    /**
     * 修改文档的 向量化  处理状态
     *
     * @param docId
     * @param hasEmbedding
     * @param userContext
     */
    void changeHasEmbeddingStatus(Long docId, Boolean hasEmbedding, UserContext userContext);

    /**
     * 查询文档状态
     * 
     * @param docIds 文档ID列表
     * @return 文档状态列表
     */
    List<KnowledgeDocumentModel> queryDocStatus(List<Long> docIds);

    /**
     * 根据知识库id查询文档
     * 
     * @param kbId 知识库id
     * @return 文档列表
     */
    List<KnowledgeDocumentModel> queryDocByKbId(Long kbId);

    /**
     * 批量根据文档ID查询文档信息（用于全文检索补充文档名称）
     * 
     * @param docIds 文档ID列表
     * @return 文档列表
     */
    List<KnowledgeDocumentModel> queryListByIds(List<Long> docIds);
}
