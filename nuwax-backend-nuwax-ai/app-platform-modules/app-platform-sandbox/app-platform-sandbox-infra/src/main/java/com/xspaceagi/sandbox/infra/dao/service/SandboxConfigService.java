package com.xspaceagi.sandbox.infra.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.sandbox.infra.dao.entity.SandboxConfig;

import java.util.List;

/**
 * 沙盒配置服务接口
 */
public interface SandboxConfigService extends IService<SandboxConfig> {

    /**
     * 根据用户ID查询配置列表
     *
     * @param userId 用户ID
     * @return 配置列表
     */
    List<SandboxConfig> queryUserConfigsByType(Long userId);

    /**
     * 查询全局配置列表
     *
     * @return 配置列表
     */
    List<SandboxConfig> queryGlobalConfigs(Boolean isActive);

    /**
     * 根据配置键查询用户配置
     *
     * @param configKey  配置键
     * @return 配置信息
     */
    SandboxConfig queryUserConfigByKey(String configKey);

    /**
     * 根据配置键查询全局配置
     *
     * @param configKey  配置键
     * @return 配置信息
     */
    SandboxConfig queryGlobalConfigByKey(String configKey);

    SandboxConfig querySandboxConfigById(Long sandboxId);
}
