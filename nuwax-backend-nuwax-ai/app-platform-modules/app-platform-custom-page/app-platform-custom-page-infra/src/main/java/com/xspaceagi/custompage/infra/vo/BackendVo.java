package com.xspaceagi.custompage.infra.vo;

import com.xspaceagi.custompage.sdk.dto.PublishTypeEnum;
import lombok.Data;

@Data
public class BackendVo {

    private String scheme;
    private String host;
    private Integer port;
    private String uri;
    private boolean requireAuth;
    private PublishTypeEnum publishType;
    private Long devAgentId;
}
