package com.xspaceagi.system.spec.auth;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({UserAuthProperties.class,UserJwtProperties.class})
public class AuthWebConfiguration {



}
