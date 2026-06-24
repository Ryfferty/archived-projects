package com.xspaceagi.system.web.dto;

import lombok.Data;

@Data
public class PullMessageRequestDto {

    private String tenantName;
    private String siteUrl;
    private String clientId;
    private String clientSecret;
    private String installationSource;
    private String version;
    private String user;
    private String uid;
}
