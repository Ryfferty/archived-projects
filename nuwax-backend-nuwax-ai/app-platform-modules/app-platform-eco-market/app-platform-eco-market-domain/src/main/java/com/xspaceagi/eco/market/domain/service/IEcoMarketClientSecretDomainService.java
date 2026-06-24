package com.xspaceagi.eco.market.domain.service;

import com.xspaceagi.eco.market.domain.model.EcoMarketClientSecretModel;
import com.xspaceagi.eco.market.sdk.model.ClientSecretDTO;
import com.xspaceagi.system.spec.common.UserContext;

import java.util.List;

/**
 * 生态市场客户端密钥服务接口
 */
public interface IEcoMarketClientSecretDomainService {

    /**
     * 查询客户端密钥详情
     *
     * @param id 配置id
     * @return 客户端密钥模型
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
     * 查询所有客户端密钥
     * 
     * @return 客户端密钥列表
     */
    List<EcoMarketClientSecretModel> queryAllList();

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
     * @param userContext 用户上下文
     * @return id
     */
    Long updateInfo(EcoMarketClientSecretModel model, UserContext userContext);

    /**
     * 新增
     * 
     * @param model 模型
     * @param userContext 用户上下文
     * @return id
     */
    Long addInfo(EcoMarketClientSecretModel model, UserContext userContext);

    /**
     * 从DTO保存客户端密钥
     * 
     * @param clientSecretDTO 客户端密钥DTO
     * @param userContext 用户上下文
     * @return 保存后的id
     */
    Long saveFromClientSecretDTO(ClientSecretDTO clientSecretDTO, UserContext userContext);

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
     * 检查租户是否已有客户端密钥
     *
     * @param tenantId 租户ID
     * @return 是否存在
     */
    boolean existsClientSecret(Long tenantId);

    /**
     * 获取或注册客户端密钥
     *
     * @param tenantId 租户ID
     * @param name 名称，注册时必填
     * @param description 描述，注册时必填
     * @return 客户端密钥DTO
     */
    ClientSecretDTO getOrRegisterClientSecret(Long tenantId, String name, String description);

    /**
     * 根据租户ID获取客户端密钥
     *
     * @param tenantId 租户ID
     * @return 客户端密钥DTO
     */
    ClientSecretDTO getByTenantId(Long tenantId);

    /**
     * 根据客户端ID获取客户端密钥
     *
     * @param clientId 客户端ID
     * @return 客户端密钥DTO
     */
    ClientSecretDTO getByClientId(String clientId);

    /**
     * 验证客户端密钥
     *
     * @param clientId 客户端ID
     * @param clientSecret 客户端密钥
     * @return 是否有效
     */
    boolean validateClientSecret(String clientId, String clientSecret);
} 