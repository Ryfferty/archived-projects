package com.xspaceagi.knowledge.domain.repository;

import com.xspaceagi.knowledge.domain.model.KnowledgeRawSegmentModel;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.infra.service.IQueryViewService;

import java.util.List;

/**
 * 知识库分段仓库
 */
public interface IKnowledgeRawSegmentRepository extends IQueryViewService<KnowledgeRawSegmentModel> {

    /**
     * 模板配置详情
     *
     * @param id 模板id
     */
    KnowledgeRawSegmentModel queryOneInfoById(Long id);

    /**
     * 批量根据主键id查询
     *
     * @param ids rawIds
     * @return List<KnowledgeRawSegmentModel>
     */
    List<KnowledgeRawSegmentModel> queryListInfoByIds(List<Long> ids);

    /**
     * 根据文档id查询分段信息
     *
     * @param docId 文档id
     */
    List<KnowledgeRawSegmentModel> queryListByDocId(Long docId);

    /**
     * 查询待生成问答的分段信息,根据字段:qaStatus=-1,created在days天前
     * @param days 天数
     * @param pageSize 每页大小
     * @param pageNum 页码
     * @return
     */
    List<KnowledgeRawSegmentModel> queryListForPendingQa(Integer days,Integer pageSize,Integer pageNum);

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
    List<KnowledgeRawSegmentModel>  queryListForPendingQaByDocId(Long docId);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 根据文档的id,进行批量删除
     *
     * @param docId 文档id
     */
    void deleteByConfigDocumentId(Long docId);
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
     * 批量更新问答状态
     *
     * @param segments 分段列表
     * @param userContext 用户上下文
     */
    void batchUpdateQaStatus(List<KnowledgeRawSegmentModel> segments, UserContext userContext);

    // ========== 全文检索相关方法 ========== //

    /**
     * 分页查询知识库的所有分段（用于全文检索数据迁移）
     *
     * @param kbId 知识库ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分段列表
     */
    List<KnowledgeRawSegmentModel> queryByKbIdWithPage(Long kbId, Integer pageNum, Integer pageSize);

    /**
     * 查询知识库的所有分段ID（用于全文检索数据一致性检查）
     *
     * @param kbId 知识库ID
     * @return 分段ID集合
     */
    List<Long> queryAllSegmentIdsByKbId(Long kbId);

    /**
     * 统计知识库的分段数量
     *
     * @param kbId 知识库ID
     * @return 分段数量
     */
    Long countByKbId(Long kbId);

    /**
     * 查询未同步的分段列表（分页）
     *
     * @param kbId 知识库ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 未同步的分段列表
     */
    List<KnowledgeRawSegmentModel> queryUnsyncedSegments(Long kbId, Integer offset, Integer limit);

    /**
     * 统计未同步的分段数量
     *
     * @param kbId 知识库ID
     * @return 未同步分段数量
     */
    Long countUnsyncedByKbId(Long kbId);

    /**
     * 批量更新分段同步状态
     *
     * @param segmentIds 分段ID列表
     * @param status 同步状态
     */
    void batchUpdateSyncStatus(List<Long> segmentIds, Integer status);

    /**
     * 查询知识库已同步的分段数量
     *
     * @param kbId 知识库ID
     * @return 已同步分段数量
     */
    Long countSyncedByKbId(Long kbId);

    /**
     * 查询所有分段ID（用于数据一致性检查）
     *
     * @param kbId 知识库ID
     * @return 分段ID列表
     */
    List<Long> queryAllSegmentIds(Long kbId);

}
