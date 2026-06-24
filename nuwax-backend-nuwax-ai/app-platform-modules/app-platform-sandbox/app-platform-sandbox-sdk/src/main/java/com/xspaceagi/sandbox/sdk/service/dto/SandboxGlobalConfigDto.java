package com.xspaceagi.sandbox.sdk.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "沙盒全局配置")
@Data
public class SandboxGlobalConfigDto {

    @Schema(description = "每个用户分配的内存大小")
    private String perUserMemoryGB;

    @Schema(description = "每个用户分配的CPU核数")
    private String perUserCpuCores;
}
