package com.xspaceagi.system.spec.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@Setter
public class LoginWrapper {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 是否登录
     */
    private boolean isLogin;

    /**
     * 用户信息
     */
    private UserContext user;
}