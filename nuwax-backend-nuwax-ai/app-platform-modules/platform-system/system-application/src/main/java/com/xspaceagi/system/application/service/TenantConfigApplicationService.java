package com.xspaceagi.system.application.service;

import com.xspaceagi.system.application.dto.TenantAddDto;
import com.xspaceagi.system.application.dto.TenantConfigDto;
import com.xspaceagi.system.application.dto.TenantConfigItemDto;
import com.xspaceagi.system.application.dto.TenantDto;
import com.xspaceagi.system.infra.dao.entity.Tenant;

import java.util.List;

public interface TenantConfigApplicationService {

    /**
     * 获取租户配置列表
     */
    List<TenantConfigItemDto> getTenantConfigList();

    List<TenantDto> getTenantList();

    TenantDto queryTenantById(Long tenantId);

    /**
     * 更新租户配置
     */
    void updateConfig(TenantConfigDto tenantConfigDto);

    /**
     * 获取租户配置
     *
     * @param tenantId
     * @return
     */
    TenantConfigDto getTenantConfig(Long tenantId);

    /**
     * 根据域名查询租户ID
     */
    Long queryTenantIdByDomainName(String domainName);

    void addTenant(TenantAddDto tenant);

    void updateTenant(Tenant tenant);
}
