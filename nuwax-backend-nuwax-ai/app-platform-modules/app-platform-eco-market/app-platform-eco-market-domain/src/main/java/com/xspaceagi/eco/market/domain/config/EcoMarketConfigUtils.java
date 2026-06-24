package com.xspaceagi.eco.market.domain.config;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.xspaceagi.system.spec.utils.MySpringBeanContextUtil;

/**
 * 生态市场配置工具类
 * 提供静态方法访问生态市场配置
 */
@Component
@DependsOn({"ecoMarketConfig", "ecoMarketProperties"})
public class EcoMarketConfigUtils {

    /**
     * 获取生态市场配置
     *
     * @return 生态市场配置
     * @throws IllegalStateException 如果ApplicationContext未初始化
     */
    public static EcoMarketConfig getEcoMarketConfig() {
        return MySpringBeanContextUtil.get(EcoMarketConfig.class);
    }

    /**
     * 获取生态市场属性
     *
     * @return 生态市场属性
     * @throws IllegalStateException 如果ApplicationContext未初始化
     */
    public static EcoMarketProperties getEcoMarketProperties() {
        return MySpringBeanContextUtil.get(EcoMarketProperties.class);
    }

    /**
     * 获取服务器基础URL
     *
     * @return 服务器基础URL
     * @throws IllegalStateException 如果ApplicationContext未初始化
     */
    public static String getServerBaseUrl() {
        return getEcoMarketConfig().getServerBaseUrl();
    }

    /**
     * 构建完整的API URL
     *
     * @param apiPath API路径
     * @return 完整的API URL
     * @throws IllegalStateException 如果ApplicationContext未初始化
     */
    public static String buildApiUrl(String apiPath) {
        String baseUrl = getServerBaseUrl();
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        if (apiPath.startsWith("/")) {
            apiPath = apiPath.substring(1);
        }
        return baseUrl + "/" + apiPath;
    }
}