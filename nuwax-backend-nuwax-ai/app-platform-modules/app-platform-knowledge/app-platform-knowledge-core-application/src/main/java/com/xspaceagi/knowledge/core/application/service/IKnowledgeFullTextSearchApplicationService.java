package com.xspaceagi.knowledge.core.application.service;

import com.xspaceagi.knowledge.core.spec.dto.fulltext.FullTextSearchRequestDto;
import com.xspaceagi.knowledge.core.spec.dto.fulltext.FullTextSearchResultDto;
import com.xspaceagi.knowledge.domain.model.fulltext.FullTextSearchRequestModel;
import com.xspaceagi.knowledge.domain.model.fulltext.FullTextSearchResultModel;
import com.xspaceagi.knowledge.domain.model.fulltext.FullTextStatsModel;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

/**
 * 知识库全文检索应用服务接口
 * 
 * @author system
 * @date 2025-03-31
 */
public interface IKnowledgeFullTextSearchApplicationService {

    /**
     * 全文检索（内部方法，需要权限校验）
     * 
     * @param request 检索请求
     * @param userContext 用户上下文
     * @return 检索结果列表
     */
    List<FullTextSearchResultModel> search(FullTextSearchRequestModel request, UserContext userContext);

    /**
     * 全文检索（使用 spec DTO，供 Controller 调用，需要权限校验）
     * 
     * @param requestDto 检索请求 DTO
     * @param userContext 用户上下文
     * @return 检索结果列表
     */
    List<FullTextSearchResultDto> searchFullText(FullTextSearchRequestDto requestDto, UserContext userContext);

    /**
     * 全文检索（供 RPC 服务调用，不做权限校验）
     * 
     * @param requestModel 检索请求 Model
     * @param tenantId 租户ID
     * @return 检索结果列表
     */
    List<FullTextSearchResultModel> searchFullTextForRpc(FullTextSearchRequestModel requestModel, Long tenantId);

    /**
     * 获取统计信息
     * 
     * @param kbId 知识库ID（可选）
     * @param userContext 用户上下文
     * @return 统计信息
     */
    FullTextStatsModel getStats(Long kbId, UserContext userContext);
}
