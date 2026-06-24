package com.xspaceagi.system.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "小程序code登录")
@Data
public class MPCodeLoginDto implements Serializable {

    @NotNull
    @Schema(description = "小程序端拿到的code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;
}
