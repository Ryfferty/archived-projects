package com.xspaceagi.knowledge.core.application.service;

import com.xspaceagi.knowledge.domain.model.KnowledgeDocumentModel;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

public interface IKnowledgeDocumentApplicationService {

    /**
     * 模板配置详情
     *
     * @param id 模板id
     */
    KnowledgeDocumentModel queryOneInfoById(Long id);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id, UserContext userContext);

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
     * @param docId       docId
     * @param name        文件名称
     * @param userContext 用户上下文
     * @return
     */
    Long updateDocName(Long docId, String name, UserContext userContext);

    /**
     * 自定义文本内容,新增,用户手动输入文档名称, 和文档内容
     */
    Long customAddInfo(KnowledgeDocumentModel model, UserContext userContext);

    /**
     * 批量新增
     *
     * @param modelList   多个文件
     * @param userContext 用户上下文
     * @return
     */
    List<Long> batchAddInfo(List<KnowledgeDocumentModel> modelList, UserContext userContext);

    /**
     * 根据文档,批量生产QA对
     *
     * @param docId 文档id
     */
    void generateForQa(Long docId, UserContext userContext);

    /**
     * 生成嵌入是基于问答分段
     *
     * @param docId
     */
    void generateEmbeddings(Long docId, UserContext userContext);

    /**
     * 自动重试,如果有分段,问答,向量化有失败的话,只对最近几天有效
     *
     * @param days        重试最近xx天失败的内容
     * @param userContext 用户上下文
     */
    void autoRetryTaskByDays(Integer days, UserContext userContext);

    /**
     * 文档id,重试所有流程,分段,问答,向量化
     *
     * @param docId       文档id
     * @param userContext 用户上下文
     */
    void retryAllTaskByDocId(Long docId, UserContext userContext);

    /**
     * 处理文档重试相关的操作
     * 
     * @param docId       文档ID
     * @param spaceId     空间ID
     * @param existObj    现有文档对象
     * @param userContext 用户上下文
     */
    void processDocumentRetry(Long docId, Long spaceId, KnowledgeDocumentModel existObj, UserContext userContext);

    /**
     * 查询文档状态
     * 
     * @param docIds 文档ID列表
     * @return 文档状态列表
     */
    List<KnowledgeDocumentModel> queryDocStatus(List<Long> docIds, UserContext userContext);

}
