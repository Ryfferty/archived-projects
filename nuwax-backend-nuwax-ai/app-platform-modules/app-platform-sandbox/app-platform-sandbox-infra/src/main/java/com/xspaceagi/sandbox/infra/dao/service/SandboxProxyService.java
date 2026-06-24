package com.xspaceagi.sandbox.infra.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.sandbox.infra.dao.entity.SandboxProxy;
import com.xspaceagi.sandbox.infra.dao.vo.SandboxProxyBackend;

import java.util.List;

/**
 * 临时代理服务接口
 */
public interface SandboxProxyService extends IService<SandboxProxy> {

    /**
     * 根据代理键查询代理配置
     *
     * @param proxyKey 代理键
     * @return 代理配置
     */
    SandboxProxy getByProxyKey(String proxyKey);

    /**
     * 根据沙盒ID查询代理配置
     *
     * @param sandboxId 沙盒ID
     * @return 代理配置
     */
    SandboxProxy getBySandboxId(Long sandboxId);

    SandboxProxyBackend getBackendByProxyKey(String proxyKey);

    /**
     * 根据用户ID查询代理配置列表
     *
     * @param userId 用户ID
     * @return 代理配置列表
     */
    java.util.List<SandboxProxy> listByUserId(Long userId);
    

    void removeByProxyKey(Long userId, String proxyKey);

    List<SandboxProxy> listByUserIdAndSandboxId(Long userId, Long sandboxId);
}
