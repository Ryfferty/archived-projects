package com.xspaceagi.knowledge.domain.vectordb;

import com.xspaceagi.knowledge.core.spec.dto.fulltext.FullTextSearchRequestDto;
import com.xspaceagi.knowledge.core.spec.dto.fulltext.FullTextSearchResultDto;
import com.xspaceagi.knowledge.core.spec.dto.fulltext.RawSegmentFullTextDto;
import com.xspaceagi.knowledge.domain.dto.qa.QAEmbeddingDto;
import com.xspaceagi.knowledge.domain.dto.qa.QAQueryEmbeddingDto;
import com.xspaceagi.knowledge.domain.dto.qa.QAResDto;

import java.util.List;

public interface VectorDBService {

    /**
     * 检查向量库是否存在
     *
     * @param collectionName 向量集合名
     * @return true:存在
     */
    boolean checkCollectionExists(String collectionName);

    /**
     * 检查向量库和文档id,是否存在
     *
     * @param collectionName 知识库
     * @param docId          文档id
     * @return true:存在
     */
    boolean checkCollectionAndDocExists(String collectionName, Long docId);

    /**
     * 检查向量库和问答ID,是否存在
     *
     * @param collectionName 知识库
     * @param qaId           问答id
     * @return
     */
    boolean checkCollectionAndQaExists(String collectionName, Long qaId);

    /**
     * 检查向量库是否存在,不存在则进行初始化,一个知识库对应一个collection
     */
    void initAndCheckCollection(Long kbId, Long embeddingModelId);

    /**
     * 删除原始文档关联的
     *
     * @param docId
     */
    void removeDoc(Long kbId,Long docId);

    /**
     * 根据问答ids,删除
     *
     * @param qaIds 问答ids
     * @param kbId  知识库
     */
    void removeQa(List<Long> qaIds, Long kbId);

    /**
     * 添加多个问答
     *
     * @param qaEmbeddingDto 问答列表
     * @param isUpdate       是否更新
     */
    void addEmbeddingQaForBatch(List<QAEmbeddingDto> qaEmbeddingDto, boolean isUpdate);

    /**
     * 添加一个问答
     *
     * @param qaEmbeddingDto 问答
     * @param isUpdate       是否更新
     */
    void addEmbeddingQa(QAEmbeddingDto qaEmbeddingDto, boolean isUpdate);

    /**
     * 搜索某个知识库
     */
    List<QAResDto> searchEmbedding(QAQueryEmbeddingDto qaQueryDto);

    /**
     * 删除某个知识库
     *
     * @param qaID 问答ID
     */
    void removeEmbeddingQA(Long qaID, Long kbId);

    /**
     * 删除多个问答
     * 
     * @param docId 文档id
     * @param qaIds 问答ids
     */
    void removeEmbeddingQaIds(Long docId, List<Long> qaIds);

    /**
     * 删除某个知识库的collection
     *
     * @param kbId 知识库ID
     */
    void deleteCollection(Long kbId);
}
