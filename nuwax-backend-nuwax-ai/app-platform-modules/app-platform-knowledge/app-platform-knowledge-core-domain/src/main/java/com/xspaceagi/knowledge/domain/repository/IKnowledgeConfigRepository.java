package com.xspaceagi.knowledge.domain.repository;

import com.xspaceagi.knowledge.domain.model.KnowledgeConfigModel;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.infra.service.IQueryViewService;

import java.util.List;

/**
 * 知识库配置
 */
public interface IKnowledgeConfigRepository extends IQueryViewService<KnowledgeConfigModel> {

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
     */
    void deleteById(Long id);

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
     * 用户操作修改文档,更新知识库的最后修改是极爱你
     * 
     * @param id          知识库id
     * @param userContext 用户上下文
     */
    void updateLatestModifyTime(Long id, UserContext userContext);

    /**
     * 更新知识库的文件大小,查询知识库下总文件大小,是一个预估值
     * 
     * @param kbId        知识库id
     * @param userContext 用户上下文
     */
    void updateKnowledgeConfigFileSize(Long kbId, UserContext userContext);

    /**
     * 查询知识库的文件大小
     * 
     * @param kbId 知识库id
     * @return 文件大小
     */
    Long queryTotalFileSize(Long kbId);

    /**
     * 查询所有知识库ID（用于全文检索数据迁移）
     * 
     * @return 知识库ID列表
     */
    List<Long> queryAllKbIds();

    /**
     * 根据知识库ID查询空间ID
     *
     * @param kbId 知识库ID
     * @return 空间ID
     */
    Long querySpaceIdByKbId(Long kbId);

    /**
     * 查询未同步的知识库列表
     *
     * @param tenantId 租户ID
     * @return 未同步的知识库列表
     */
    List<KnowledgeConfigModel> queryUnsyncedKnowledgeBases(Long tenantId);

    /**
     * 更新知识库全文检索同步状态
     *
     * @param kbId 知识库ID
     * @param status 同步状态
     * @param segmentCount 分段数量
     */
    void updateFulltextSyncStatus(Long kbId, Integer status, Long segmentCount);

    /**
     * 更新知识库全文检索同步状态，不包含分段数量
     *
     * @param kbId 知识库ID
     * @param status 同步状态, 全文检索同步状态: 0-未同步, 1-已同步
     */
    void updateFulltextSyncStatus(Long kbId, Integer status);

    /**
     * 查询同步状态统计
     *
     * @param tenantId 租户ID
     * @return 同步状态统计
     */
    java.util.Map<Integer, Long> querySyncStatusStats(Long tenantId);

    /**
     * 统计知识库总数
     *
     * @return 知识库总数
     */
    Long countTotalKnowledge(Long userId);

}
