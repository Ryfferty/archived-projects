package com.xspaceagi.eco.market.spec.infra.dao.service;

import com.xspaceagi.eco.market.spec.infra.dao.entity.EcoMarketClientSecret;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author soddy
 * @description 针对表【eco_market_client_secret(生态市场,客户端端配置)】的数据库操作Service
 * @createDate 2025-05-26 17:08:22
 */
public interface EcoMarketClientSecretService extends IService<EcoMarketClientSecret> {
  /**
   * 根据ID集合查询列表
   *
   * @param ids id集合
   * @return 列表
   */
  List<EcoMarketClientSecret> queryListByIds(List<Long> ids);

  /**
   * 根据主键查询 单条记录
   * 
   * @param id id
   * @return 单条记录
   */
  EcoMarketClientSecret queryOneInfoById(Long id);

  /**
   * 更新
   *
   * @param entity entity
   * @return id
   */
  Long updateInfo(EcoMarketClientSecret entity);

  /**
   * 新增
   */
  Long addInfo(EcoMarketClientSecret entity);

  /**
   * 删除根据主键id
   *
   * @param id id
   */
  void deleteById(Long id);

  /**
   * 根据租户ID查询客户端密钥
   *
   * @param tenantId 租户ID
   * @return 客户端密钥
   */
  EcoMarketClientSecret getByTenantId(Long tenantId);

  /**
   * 根据客户端ID查询客户端密钥
   *
   * @param clientId 客户端ID
   * @return 客户端密钥
   */
  EcoMarketClientSecret getByClientId(String clientId);

  /**
   * 查询所有客户端密钥
   * 
   * @return 客户端密钥列表
   */
  List<EcoMarketClientSecret> queryAllList();
}
