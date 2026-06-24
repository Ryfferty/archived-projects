package com.xspaceagi.system.sdk.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class UserShareDto {
    private Long id;
    private String shareKey;
    private Long tenantId;
    @Schema(description = "用户信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long userId;
    @Schema(description = "分享类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private UserShareType type;
    private String targetId;
    private Object content;
    //有效期
    private Date expire;
    private Date modified;
    private Date created;

    public enum UserShareType {
        CONVERSATION,
        DESKTOP,
        MESSAGE,
    }
}
