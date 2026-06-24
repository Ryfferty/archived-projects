package com.xspaceagi.custompage.sdk.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProxyConfig {
    // 环境，比如 dev 为开发环境；prod 为生产环境
    private ProxyEnv env;
    // 路径，比如 /education代表/education后的所有请求都会被代理到${backends}
    private String path;
    private List<ProxyConfigBackend> backends;
    private String healthCheckPath;
    // 是否必须登录，默认true
    private boolean requireAuth;

    public enum ProxyEnv {
        dev, prod;

        public static ProxyEnv get(String value) {
            for (ProxyEnv env : values()) {
                if (env.name().equals(value)) {
                    return env;
                }
            }
            return null;
        }
    }
}