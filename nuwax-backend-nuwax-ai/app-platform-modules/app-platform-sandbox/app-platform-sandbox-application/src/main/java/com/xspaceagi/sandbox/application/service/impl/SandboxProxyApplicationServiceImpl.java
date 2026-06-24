package com.xspaceagi.sandbox.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xspaceagi.sandbox.application.dto.SandboxProxyDto;
import com.xspaceagi.sandbox.application.service.SandboxProxyApplicationService;
import com.xspaceagi.sandbox.infra.dao.entity.SandboxProxy;
import com.xspaceagi.sandbox.infra.dao.service.SandboxProxyService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

/**
 * 临时代理应用服务实现
 */
@Service
public class SandboxProxyApplicationServiceImpl implements SandboxProxyApplicationService {

    @Resource
    private SandboxProxyService sandboxProxyService;


    @Override
    public SandboxProxyDto create(SandboxProxyDto dto) {
        Assert.notNull(dto.getUserId(), "用户ID不能为空");
        Assert.notNull(dto.getSandboxId(), "沙盒ID不能为空");
        Assert.notNull(dto.getBackendHost(), "后端主机地址不能为空");
        Assert.notNull(dto.getBackendPort(), "后端端口不能为空");

        SandboxProxy sandboxProxy = new SandboxProxy();
        sandboxProxy.setUserId(dto.getUserId());
        sandboxProxy.setSandboxId(dto.getSandboxId());
        sandboxProxy.setProxyKey(UUID.randomUUID().toString().replace("-", ""));
        sandboxProxy.setBackendHost(dto.getBackendHost());
        sandboxProxy.setBackendPort(dto.getBackendPort());

        sandboxProxyService.save(sandboxProxy);
        return toDto(sandboxProxy);
    }

    @Override
    public List<SandboxProxyDto> querySandboxyProxyList(Long userId, Long sandboxId) {
        return sandboxProxyService.listByUserIdAndSandboxId(userId, sandboxId).stream().map(this::toDto).toList();
    }

    @Override
    public void deleteByProxyKey(Long userId, String proxyKey) {
        sandboxProxyService.removeByProxyKey(userId, proxyKey);
    }

    @Override
    public void deleteById(Long userId, Long id) {
        QueryWrapper<SandboxProxy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("user_id", userId);
        sandboxProxyService.remove(queryWrapper);
    }

    private SandboxProxyDto toDto(SandboxProxy sandboxProxy) {
        if (sandboxProxy == null) {
            return null;
        }
        SandboxProxyDto dto = new SandboxProxyDto();
        BeanUtils.copyProperties(sandboxProxy, dto);
        return dto;
    }
}
