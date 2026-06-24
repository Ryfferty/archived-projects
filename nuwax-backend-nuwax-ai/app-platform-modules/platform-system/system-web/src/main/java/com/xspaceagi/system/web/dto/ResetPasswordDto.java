package com.xspaceagi.system.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResetPasswordDto implements Serializable {

    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;
}
