package com.xspaceagi.knowledge.domain.service;

import com.xspaceagi.knowledge.core.spec.enums.KnowledgeTaskRunTypeEnum;
import com.xspaceagi.knowledge.domain.model.KnowledgeDocumentIdAndTaskIdVo;
import com.xspaceagi.knowledge.domain.model.KnowledgeDocumentModel;
import com.xspaceagi.knowledge.domain.model.KnowledgeQaSegmentModel;
import com.xspaceagi.knowledge.domain.model.KnowledgeRawSegmentModel;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

public interface IKnowledgeDocumentDomainService {

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
         * 新增,会加事务
         */
        KnowledgeDocumentIdAndTaskIdVo addDocument(KnowledgeDocumentModel model, UserContext userContext);

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
         * 文档分段,问答,向量化任务
         *
         * @param model       文档
         * @param userContext 用户
         * @param docId       文档id
         */
        void workRunTaskForDocument(KnowledgeDocumentModel model, UserContext userContext, Long docId);

        /**
         * 生成分段
         * 
         * @param model       文档
         * @param userContext 用户
         * @param docId       文档id
         */
        void workRunTaskForRawSegment(KnowledgeDocumentModel model, UserContext userContext, Long docId);

        /**
         * 生成问答和向量化任务
         * 
         * @param model       文档
         * @param userContext 用户
         * @param docId       文档id
         */
        void workRunTaskForQaAndEmbedding(KnowledgeDocumentModel model, UserContext userContext, Long docId);

        /**
         * 分节点,进行区分,和重试
         *
         * @param runTypeEnum
         * @param model
         * @param userContext
         * @param docId
         */
        void workRetryRunTaskForDocument(KnowledgeTaskRunTypeEnum runTypeEnum, KnowledgeDocumentModel model,
                        UserContext userContext, Long docId);

        /**
         * 根据文档,批量生产QA对
         *
         * @param docId 文档id
         */
        void generateForQa(Long docId, UserContext userContext);


        /**
         * 检查 docId 下的rawText是否全部生成QA了,是的话,更新状态
         * @param docId
         * @param userContext
         */
        void checkIsQaFinishAndChangeDocStatus(Long docId, UserContext userContext);


        /**
         * 根据分段,批量生产QA对,并且生成QA的向量化(提交到另外一个线程里执行向量化)
         *
         * @param segments    分段
         * @param userContext 用户
         */
        void generateForQaByRawSegment(List<KnowledgeRawSegmentModel> segments, UserContext userContext);


        /**
         * 生成嵌入是基于问答分段
         *
         * @param docID
         */
        void generateEmbeddings(Long docID, UserContext userContext);

        /**
         * 生成问答的向量化,是基于问答分段,用分段id查询问答
         *
         * @param segments    分段
         * @param userContext 用户
         */
        void generateQaEmbeddingsByRawSegment(List<KnowledgeRawSegmentModel> segments, UserContext userContext);

        /**
         * 生成问答的向量化,是基于问答分段,用分段id查询问答
         *
         * @param segments    分段
         * @param userContext 用户
         */
        void generateEmbeddingsByQaSegment(List<KnowledgeQaSegmentModel> segments, UserContext userContext);

        /**
         * 更新文档的嵌入状态,检查每个分段是否生成了对应的问答,如果生成了,且问答都向量化完毕,则更新文档的嵌入状态
         *
         * @param docId       文档ID
         * @param userContext 用户上下文
         */
        void updateDocumentEmbeddingStatus(Long docId, UserContext userContext);

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
}
