package com.xspaceagi.eco.market.spec.app.service;

import com.xspaceagi.eco.market.domain.model.EcoMarketClientSecretModel;
import com.xspaceagi.eco.market.sdk.model.ClientSecretDTO;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

public interface IEcoMarketClientSecretApplicationService {
    
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
     * 保存客户端密钥DTO
     * 
     * @param clientSecretDTO 客户端密钥DTO
     * @param userContext 用户上下文
     * @return 密钥ID
     */
    Long saveClientSecretDTO(ClientSecretDTO clientSecretDTO, UserContext userContext);
    
    /**
     * 检查租户是否已有客户端密钥
     *
     * @param tenantId 租户ID
     * @return 是否存在
     */
    boolean existsClientSecret(Long tenantId);


}
