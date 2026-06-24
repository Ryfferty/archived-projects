package com.xspaceagi.eco.market.domain.repository;

import java.util.List;
import java.util.Map;

import com.xspaceagi.eco.market.domain.model.EcoMarketClientSecretModel;
import com.xspaceagi.system.spec.common.UserContext;

public interface IEcoMarketClientSecretRepository {

    /**
     * 客户端密钥详情
     *
     * @param id 密钥id
     */
    EcoMarketClientSecretModel queryOneInfoById(Long id);

    /**
     * 批量根据ids查询客户端密钥
     * 
     * @param ids id列表
     * @return 客户端密钥列表
     */
    List<EcoMarketClientSecretModel> queryListByIds(List<Long> ids);

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
    Long updateInfo(EcoMarketClientSecretModel model, UserContext userContext);

    /**
     * 新增
     */
    Long addInfo(EcoMarketClientSecretModel model, UserContext userContext);
    
    /**
     * 根据租户ID查询客户端密钥
     * 
     * @param tenantId 租户ID
     * @return 客户端密钥模型
     */
    EcoMarketClientSecretModel queryByTenantId(Long tenantId);
    
    /**
     * 根据客户端ID查询客户端密钥
     * 
     * @param clientId 客户端ID
     * @return 客户端密钥模型
     */
    EcoMarketClientSecretModel queryByClientId(String clientId);
    
    /**
     * 根据参数查询客户端密钥列表
     * 
     * @param params 查询参数
     * @return 客户端密钥列表
     */
    List<EcoMarketClientSecretModel> queryListByParams(Map<String, Object> params);

    /**
     * 查询所有客户端密钥
     * 
     * @return 客户端密钥列表
     */
    List<EcoMarketClientSecretModel> queryAllList();
}
