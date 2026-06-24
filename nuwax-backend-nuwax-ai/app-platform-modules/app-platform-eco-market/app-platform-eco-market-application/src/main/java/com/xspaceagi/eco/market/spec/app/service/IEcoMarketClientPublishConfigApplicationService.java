package com.xspaceagi.eco.market.spec.app.service;

import java.util.List;
import java.util.Map;

import com.xspaceagi.eco.market.domain.dto.req.UpdateAndEnableConfigReqDTO;
import com.xspaceagi.eco.market.domain.model.EcoMarketClientPublishConfigModel;
import com.xspaceagi.system.spec.common.UserContext;

public interface IEcoMarketClientPublishConfigApplicationService {
    
    /**
     * 已发布配置详情
     *
     * @param id 配置id
     */
    EcoMarketClientPublishConfigModel queryOneInfoById(Long id);

    /**
     * 根据uid查询已发布配置详情
     *
     * @param uid 配置唯一标识
     * @return 已发布配置详情
     */
    EcoMarketClientPublishConfigModel queryOneByUid(String uid);

    /**
     * 批量根据ids查询已发布配置
     * 
     * @param ids id列表
     * @return 已发布配置列表
     */
    List<EcoMarketClientPublishConfigModel> queryListByIds(List<Long> ids);


    /**
     * 批量根据uids查询已发布配置
     * 
     * @param uids uid列表
     * @return 已发布配置列表
     */
    List<EcoMarketClientPublishConfigModel> queryListByUids(List<String> uids);


    /**
     * 根据参数查询已发布配置列表
     * 
     * @param params 查询参数
     * @return 已发布配置列表
     */
    List<EcoMarketClientPublishConfigModel> queryListByParams(Map<String, Object> params);

    /**
     * 启用配置
     *
     * @param uid 配置唯一标识
     * @param userContext 用户上下文
     * @return 启用后的配置模型
     */
    EcoMarketClientPublishConfigModel enableConfig(String uid, UserContext userContext);

    /**
     * 禁用配置
     *
     * @param uid 配置唯一标识
     * @param userContext 用户上下文
     * @return 禁用后的配置模型
     */
    EcoMarketClientPublishConfigModel disableConfig(String uid, UserContext userContext);

    /**
     * 更新并启用配置
     *
     * @param request 更新配置
     * @param userContext 用户上下文
     * @return 启用后的配置模型
     */
    EcoMarketClientPublishConfigModel updateAndEnableConfig(UpdateAndEnableConfigReqDTO request, UserContext userContext);
}
