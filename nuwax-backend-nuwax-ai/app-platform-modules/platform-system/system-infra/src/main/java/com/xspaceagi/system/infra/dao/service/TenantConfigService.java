package com.xspaceagi.system.infra.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.system.infra.dao.entity.TenantConfig;

import java.util.List;
import java.util.Map;

public interface TenantConfigService extends IService<TenantConfig> {

    List<TenantConfig> getTenantConfigList();

    Map<String, String> getAllTenantDomainConfigMap();

    void updateConfig(Map<String, Object> config);
}
