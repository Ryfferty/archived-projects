package com.xspaceagi.system.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "验证码登录")
@Data
public class CodeLoginDto implements Serializable {

    @Schema(description = "完整的手机号码，例如 8613888888888")
    private String phone;

    @Schema(description = "完整的邮箱地址，与手机号码二选一，例如 xxx@xx.com")
    private String phoneOrEmail;

    @NotNull
    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;
}
