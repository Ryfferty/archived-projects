package com.xspaceagi.sandbox.application.service;

import com.xspaceagi.sandbox.application.dto.SandboxProxyDto;

import java.util.List;

/**
 * 临时代理应用服务接口
 */
public interface SandboxProxyApplicationService {

    /**
     * 创建代理配置
     *
     * @param dto 代理信息
     * @return 创建后的代理信息
     */
    SandboxProxyDto create(SandboxProxyDto dto);

    /**
     * 获取代理列表
     *
     * @param userId    用户id
     * @param sandboxId 沙盒id
     * @return 代理列表
     */
    List<SandboxProxyDto> querySandboxyProxyList(Long userId, Long sandboxId);

    /**
     * 删除代理配置
     *
     * @param proxyKey 代理key
     */
    void deleteByProxyKey(Long userId, String proxyKey);

    void deleteById(Long userId, Long id);
}
