package com.xspaceagi.system.infra.verify.email;

import lombok.Data;

@Data
public class SmtpConfig {
    private String siteName;
    private String host;
    private Integer port;
    private String username;
    private String password;
}
