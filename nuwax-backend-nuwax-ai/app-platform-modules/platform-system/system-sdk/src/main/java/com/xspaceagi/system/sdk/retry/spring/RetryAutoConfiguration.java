package com.xspaceagi.system.sdk.retry.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RetryAutoConfiguration {
    //后续接入MQ、RPC等
}
