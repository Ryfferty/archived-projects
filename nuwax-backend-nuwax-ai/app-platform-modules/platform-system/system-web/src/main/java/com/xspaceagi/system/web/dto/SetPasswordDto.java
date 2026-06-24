package com.xspaceagi.system.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class SetPasswordDto implements Serializable {

    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
