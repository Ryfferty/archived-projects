package com.xspaceagi.eco.market.spec.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.eco.market.domain.model.EcoMarketClientConfigModel;
import com.xspaceagi.eco.market.sdk.model.ClientSecretDTO;
import com.xspaceagi.eco.market.spec.app.dto.request.ClientConfigQueryRequest;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

public interface IEcoMarketClientConfigApplicationService {
    
    /**
     * 配置详情
     *
     * @param id 配置id
     */
    EcoMarketClientConfigModel queryOneInfoById(Long id);

    /**
     * 批量根据ids查询配置
     * 
     * @param ids id列表
     * @return 配置列表
     */
    List<EcoMarketClientConfigModel> queryListByIds(List<Long> ids);

    /**
     * 删除根据主键id
     *
     * @param id id
     */
    void deleteById(Long id);


    /**
     * 分页查询客户端配置，并与服务器配置比较
     * 
     * @param request 查询参数
     * @param current 当前页
     * @param size 每页大小
     * @param spaceIds 空间ID列表
     * @return 分页结果
     */
    IPage<EcoMarketClientConfigModel> pageQueryWithServerCompare(ClientConfigQueryRequest request, long current, long size, ClientSecretDTO clientSecret, UserContext userContext);

    /**
     * 根据uid查询客户端配置
     * 
     * @param uid 唯一ID
     * @return 客户端配置
     */
    EcoMarketClientConfigModel queryOneByUid(String uid);

    /**
     * 根据uid列表查询客户端配置
     * 
     * @param uids 唯一ID列表
     * @return 客户端配置列表
     */
    List<EcoMarketClientConfigModel> queryListByUids(List<String> uids);
    
    /**
     * 获取配置详情（包含服务器比对逻辑）
     * 
     * @param uid 配置唯一标识
     * @param tenantId 租户ID
     * @return 客户端配置，包含是否有新版本的标记
     */
    EcoMarketClientConfigModel getConfigDetail(String uid, UserContext userContext);

    /**
     * 保存并发布配置
     * 
     * @param reqDTO 请求DTO
     * @param userContext 用户上下文
     * @return 配置模型
     */
    EcoMarketClientConfigModel saveAndPublish(EcoMarketClientConfigModel model, String clientId, String clientSecret, UserContext userContext);

    /**
     * 根据uid下线配置
     * 
     * @param uid 配置UID
     * @param userContext 用户上下文
     * @return 下线后的配置模型
     */
    EcoMarketClientConfigModel offlineConfigByUid(String uid, ClientSecretDTO clientSecret,UserContext userContext);


    /**
     * 根据uid,撤销发布
     * 
     * @param uid 配置UID
     * @param userContext 用户上下文
     * @return 下线后的配置模型
     */
    void unpublishConfigByUid(String uid, ClientSecretDTO clientSecret,UserContext userContext);


    /**
     * 根据uid删除配置（包含业务检查）
     * 
     * @param uid 配置UID
     * @param userContext 用户上下文
     * @return 是否删除成功
     */
    boolean deleteConfigByUid(String uid,ClientSecretDTO clientSecret , UserContext userContext);
    
    /**
     * 更新草稿配置
     * 
     * @param model 配置模型
     * @param userContext 用户上下文
     * @return 更新后的配置模型
     */
    EcoMarketClientConfigModel updateDraft(EcoMarketClientConfigModel model, UserContext userContext);

    /**
     * 创建草稿配置
     * 
     * @param model 配置模型
     * @param clientId 客户端ID
     * @param userContext 用户上下文
     * @return 创建后的配置模型
     */
    EcoMarketClientConfigModel saveDraft(EcoMarketClientConfigModel model, String clientId, UserContext userContext);


}
