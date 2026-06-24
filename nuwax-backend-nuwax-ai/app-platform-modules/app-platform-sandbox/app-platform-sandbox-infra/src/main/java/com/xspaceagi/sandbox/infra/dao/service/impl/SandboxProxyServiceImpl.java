package com.xspaceagi.sandbox.infra.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.sandbox.infra.dao.entity.SandboxConfig;
import com.xspaceagi.sandbox.infra.dao.entity.SandboxProxy;
import com.xspaceagi.sandbox.infra.dao.mapper.SandboxProxyMapper;
import com.xspaceagi.sandbox.infra.dao.service.SandboxConfigService;
import com.xspaceagi.sandbox.infra.dao.service.SandboxProxyService;
import com.xspaceagi.sandbox.infra.dao.vo.SandboxProxyBackend;
import com.xspaceagi.system.spec.cache.SimpleJvmHashCache;
import com.xspaceagi.system.spec.tenant.thread.TenantFunctions;
import com.xspaceagi.system.spec.utils.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 临时代理服务实现
 */
@Service
public class SandboxProxyServiceImpl extends ServiceImpl<SandboxProxyMapper, SandboxProxy> implements SandboxProxyService {

    @Resource
    private SandboxConfigService sandboxConfigService;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public SandboxProxy getByProxyKey(String proxyKey) {
        LambdaQueryWrapper<SandboxProxy> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SandboxProxy::getProxyKey, proxyKey)
                .last("LIMIT 1");
        return getOne(queryWrapper);
    }

    @Override
    public SandboxProxy getBySandboxId(Long sandboxId) {
        LambdaQueryWrapper<SandboxProxy> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SandboxProxy::getSandboxId, sandboxId)
                .last("LIMIT 1");
        return getOne(queryWrapper);
    }

    @Override
    public SandboxProxyBackend getBackendByProxyKey(String proxyKey) {
        Object val = SimpleJvmHashCache.getHash(SandboxProxy.class.getName(), proxyKey);
        if (val != null) {
            return (SandboxProxyBackend) val;
        }
        SandboxProxyBackend backend = TenantFunctions.callWithIgnoreCheck(() -> {
            SandboxProxy byProxyKey = getByProxyKey(proxyKey);
            if (byProxyKey == null) {
                return null;
            }
            SandboxConfig sandboxConfig = sandboxConfigService.querySandboxConfigById(byProxyKey.getSandboxId());
            if (sandboxConfig == null) {
                return null;
            }
            return SandboxProxyBackend.builder()
                    .sandboxId(byProxyKey.getSandboxId())
                    .proxyKey(byProxyKey.getProxyKey())
                    .backendHost(byProxyKey.getBackendHost())
                    .backendPort(byProxyKey.getBackendPort())
                    .sandboxConfigKey(sandboxConfig.getConfigKey())
                    .build();
        });
        SimpleJvmHashCache.putHash(SandboxProxy.class.getName(), proxyKey, backend, 10);
        return backend;
    }

    @Override
    public List<SandboxProxy> listByUserId(Long userId) {
        LambdaQueryWrapper<SandboxProxy> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SandboxProxy::getUserId, userId)
                .orderByDesc(SandboxProxy::getCreated);
        return list(queryWrapper);
    }

    @Override
    public void removeByProxyKey(Long userId, String proxyKey) {
        LambdaQueryWrapper<SandboxProxy> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SandboxProxy::getUserId, userId)
                .eq(SandboxProxy::getProxyKey, proxyKey);
        remove(queryWrapper);
    }

    @Override
    public List<SandboxProxy> listByUserIdAndSandboxId(Long userId, Long sandboxId) {
        LambdaQueryWrapper<SandboxProxy> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SandboxProxy::getUserId, userId)
                .eq(SandboxProxy::getSandboxId, sandboxId)
                .orderByDesc(SandboxProxy::getCreated);
        return list(queryWrapper);
    }
}
