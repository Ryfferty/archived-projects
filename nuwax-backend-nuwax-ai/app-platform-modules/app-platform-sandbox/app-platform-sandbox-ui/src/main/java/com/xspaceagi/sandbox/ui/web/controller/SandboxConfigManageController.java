package com.xspaceagi.sandbox.ui.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xspaceagi.sandbox.application.dto.SandboxConfigDto;
import com.xspaceagi.sandbox.application.dto.SandboxConfigQueryDto;
import com.xspaceagi.sandbox.application.dto.SandboxGlobalConfigDto;
import com.xspaceagi.sandbox.application.service.SandboxConfigApplicationService;
import com.xspaceagi.system.spec.annotation.RequireResource;
import com.xspaceagi.system.spec.common.RequestContext;
import com.xspaceagi.system.spec.dto.ReqResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.xspaceagi.system.spec.enums.ResourceEnum.*;

/**
 * 沙盒配置管理接口
 */
@Tag(name = "系统管理-沙盒配置相关接口")
@RestController
@RequestMapping("/api/system/sandbox/config")
public class SandboxConfigManageController {

    @Resource
    private SandboxConfigApplicationService sandboxConfigApplicationService;

    @RequireResource(SANDBOX_CONFIG_QUERY)
    @Operation(summary = "分页查询配置列表")
    @PostMapping("/page")
    public ReqResult<Page<SandboxConfigDto>> pageQuery(@RequestBody SandboxConfigQueryDto queryDto) {
        return ReqResult.success(sandboxConfigApplicationService.pageQuery(queryDto));
    }

    @RequireResource(SANDBOX_CONFIG_QUERY)
    @Operation(summary = "查询配置详情")
    @GetMapping("/get/{id}")
    public ReqResult<SandboxConfigDto> getById(
            @Parameter(description = "配置ID") @PathVariable Long id) {
        return ReqResult.success(sandboxConfigApplicationService.getById(id));
    }

    @Operation(summary = "沙箱连通性测试")
    @GetMapping("/test/{id}")
    public ReqResult<Void> testConnection(@Parameter(description = "配置ID") @PathVariable Long id) {
        try {
            sandboxConfigApplicationService.testConnection(id);
        } catch (Exception e) {
            return ReqResult.error("0007", e.getMessage());
        }
        return ReqResult.success();
    }

    @RequireResource(SANDBOX_CONFIG_QUERY)
    @Operation(summary = "查询用户配置列表")
    @GetMapping("/user/list")
    public ReqResult<List<SandboxConfigDto>> listUserConfigsByType(
            @Parameter(description = "用户ID（为空时查询当前用户）") @RequestParam(required = false) Long userId) {
        return ReqResult.success(sandboxConfigApplicationService.listUserConfigsByType(userId));
    }

    @RequireResource(SANDBOX_CONFIG_QUERY)
    @Operation(summary = "查询全局配置列表")
    @GetMapping("/global/list")
    public ReqResult<List<SandboxConfigDto>> listGlobalConfigsByType() {
        return ReqResult.success(sandboxConfigApplicationService.listGlobalConfigsByType());
    }

    @RequireResource(SANDBOX_CONFIG_ADD)
    @Operation(summary = "创建配置")
    @PostMapping("/create")
    public ReqResult<Void> create(@RequestBody SandboxConfigDto dto) {
        sandboxConfigApplicationService.create(dto, false);
        return ReqResult.success();
    }

    @RequireResource(SANDBOX_CONFIG_MODIFY)
    @Operation(summary = "更新配置")
    @PostMapping("/update")
    public ReqResult<Void> update(@RequestBody SandboxConfigDto dto) {
        sandboxConfigApplicationService.update(dto);
        return ReqResult.success();
    }

    @RequireResource(SANDBOX_CONFIG_DELETE)
    @Operation(summary = "删除配置")
    @PostMapping("/delete/{id}")
    public ReqResult<Void> delete(
            @Parameter(description = "配置ID") @PathVariable Long id) {
        sandboxConfigApplicationService.delete(id);
        return ReqResult.success();
    }

    @RequireResource(SANDBOX_CONFIG_MODIFY)
    @Operation(summary = "启用/禁用配置")
    @PostMapping("/toggle/{id}")
    public ReqResult<Void> toggle(
            @Parameter(description = "配置ID") @PathVariable Long id) {
        sandboxConfigApplicationService.toggle(id);
        return ReqResult.success();
    }

    @RequireResource(SANDBOX_CONFIG_SAVE)
    @Operation(summary = "更新沙箱全局配置")
    @PostMapping("/global/update")
    public ReqResult<Void> globalUpdate(@RequestBody SandboxGlobalConfigDto dto) {
        sandboxConfigApplicationService.updateGlobalConfig(RequestContext.get().getTenantId(), dto);
        return ReqResult.success();
    }

    @RequireResource(SANDBOX_CONFIG_QUERY)
    @Operation(summary = "查询沙箱全局配置")
    @PostMapping("/global")
    public ReqResult<SandboxGlobalConfigDto> getGlobalConfig() {
        SandboxGlobalConfigDto globalConfig = sandboxConfigApplicationService.getGlobalConfig(RequestContext.get().getTenantId());
        return ReqResult.success(globalConfig);
    }
}
