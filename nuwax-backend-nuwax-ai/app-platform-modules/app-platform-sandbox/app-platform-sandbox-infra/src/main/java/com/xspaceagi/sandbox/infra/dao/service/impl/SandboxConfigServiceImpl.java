package com.xspaceagi.sandbox.infra.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.sandbox.infra.dao.entity.SandboxConfig;
import com.xspaceagi.sandbox.infra.dao.mapper.SandboxConfigMapper;
import com.xspaceagi.sandbox.infra.dao.service.SandboxConfigService;
import com.xspaceagi.sandbox.spec.enums.SandboxScopeEnum;
import com.xspaceagi.system.spec.tenant.thread.TenantFunctions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 沙盒配置服务实现
 */
@Service
public class SandboxConfigServiceImpl extends ServiceImpl<SandboxConfigMapper, SandboxConfig> implements SandboxConfigService {

    @Override
    public List<SandboxConfig> queryUserConfigsByType(Long userId) {
        LambdaQueryWrapper<SandboxConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SandboxConfig::getScope, SandboxScopeEnum.USER)
                .eq(SandboxConfig::getUserId, userId)
                .orderByDesc(SandboxConfig::getId);

        return list(queryWrapper);
    }

    @Override
    public List<SandboxConfig> queryGlobalConfigs(Boolean isActive) {
        LambdaQueryWrapper<SandboxConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SandboxConfig::getScope, SandboxScopeEnum.GLOBAL)
                .eq(isActive != null, SandboxConfig::getIsActive, isActive)
                .orderByAsc(SandboxConfig::getId);

        return list(queryWrapper);
    }

    @Override
    public SandboxConfig queryUserConfigByKey(String configKey) {
        LambdaQueryWrapper<SandboxConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SandboxConfig::getScope, SandboxScopeEnum.USER)
                .eq(SandboxConfig::getConfigKey, configKey)
                .eq(SandboxConfig::getIsActive, true)
                .last("LIMIT 1");

        return TenantFunctions.callWithIgnoreCheck(() -> getOne(queryWrapper));
    }

    @Override
    public SandboxConfig queryGlobalConfigByKey(String configKey) {
        LambdaQueryWrapper<SandboxConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SandboxConfig::getScope, SandboxScopeEnum.GLOBAL)
                .eq(SandboxConfig::getConfigKey, configKey)
                .eq(SandboxConfig::getIsActive, true)
                .last("LIMIT 1");

        return getOne(queryWrapper);
    }

    @Override
    public SandboxConfig querySandboxConfigById(Long sandboxId) {
        LambdaQueryWrapper<SandboxConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SandboxConfig::getId, sandboxId)
                .eq(SandboxConfig::getIsActive, true)
                .last("LIMIT 1");
        return getOne(queryWrapper);
    }
}
