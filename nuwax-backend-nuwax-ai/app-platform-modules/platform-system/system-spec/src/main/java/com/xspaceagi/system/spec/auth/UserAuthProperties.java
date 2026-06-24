package com.xspaceagi.system.spec.auth;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 用户鉴权相关配置
 */
@Data
@ConfigurationProperties(prefix = "auth")
public class UserAuthProperties {


    /**
     * 不需要鉴权的uri
     */
    private List<String> excludePath = Lists.newArrayList("/login", "/health", "/ready");


}
