package com.xspaceagi.system.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoQueryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名（用户名、邮箱、手机号三选一）")
    private String userName;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号码")
    private String phone;
}
