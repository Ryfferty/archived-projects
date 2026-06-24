package com.xspaceagi.custompage.domain.proxypath;

import com.xspaceagi.custompage.domain.model.CustomPageConfigModel;

/**
 * dev/prod代理路径生成，并支持缓存优化
 */
public interface ICustomPageProxyPathService {

    /**
     * 获取dev代理路径
     */
    String getDevProxyPath(Long projectId);

    /**
     * 获取dev代理路径
     */
    String getDevProxyPath(CustomPageConfigModel configModel);

    /**
     * 获取prod代理路径
     */
    String getProdProxyPath(Long projectId);

    /**
     * 获取prod代理路径
     */
    String getProdProxyPath(CustomPageConfigModel configModel);

    /**
     * 清除指定项目的缓存
     */
    void removeConfigCache(Long projectId);
}