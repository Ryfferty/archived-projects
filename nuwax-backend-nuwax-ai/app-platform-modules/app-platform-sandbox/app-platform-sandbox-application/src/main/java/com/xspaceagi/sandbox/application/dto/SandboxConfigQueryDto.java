package com.xspaceagi.sandbox.application.dto;

import com.xspaceagi.sandbox.spec.enums.SandboxScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 沙盒配置查询 DTO
 */
@Schema(description = "沙盒配置查询条件")
@Data
public class SandboxConfigQueryDto {

    @Schema(description = "配置范围：global-全局配置 user-个人配置")
    private SandboxScopeEnum scope;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "配置名称（模糊查询）")
    private String name;

    @Schema(description = "是否启用")
    private Boolean isActive;

    @Schema(description = "页码", example = "1")
    private Integer pageNum = 1;

    @Schema(description = "每页大小", example = "10")
    private Integer pageSize = 10;
}
