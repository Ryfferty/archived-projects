package com.xspaceagi.system.spec.auth;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "auth.jwt")
public class UserJwtProperties {


    /**
     * jwt 密钥
     */
    private String secretKey;
}
