package com.xspaceagi.sandbox.ui.web.controller;

import com.xspaceagi.sandbox.application.dto.SandboxProxyDto;
import com.xspaceagi.sandbox.application.service.SandboxProxyApplicationService;
import com.xspaceagi.sandbox.ui.web.dto.SandboxTempLinkDto;
import com.xspaceagi.system.application.dto.TenantConfigDto;
import com.xspaceagi.system.spec.common.RequestContext;
import com.xspaceagi.system.spec.dto.ReqResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 沙箱内部可调用接口
 */
@Tag(name = "沙箱内部可调用接口")
@RestController
@RequestMapping("/api/v1/4sandbox")
public class ApiForSandboxController {

    @Resource
    private SandboxProxyApplicationService sandboxProxyApplicationService;

    @Value("${sandbox.temp-link.base-domain:}")
    private String baseDomain;

    @Operation(summary = "创建临时代理配置")
    @PostMapping("/proxy/create")
    public ReqResult<SandboxTempLinkDto> create(@RequestBody SandboxProxyDto dto) {
        dto.setUserId(RequestContext.get().getUserId());
        SandboxProxyDto sandboxProxyDto = sandboxProxyApplicationService.create(dto);
        SandboxTempLinkDto sandboxTempLinkDto = convertToSandboxTempLinkDto(sandboxProxyDto);
        return ReqResult.success(sandboxTempLinkDto);
    }

    @Operation(summary = "删除临时代理配置")
    @PostMapping("/proxy/delete/{id}")
    public ReqResult<Void> delete(
            @Parameter(description = "代理ID") @PathVariable Long id) {
        sandboxProxyApplicationService.deleteById(RequestContext.get().getUserId(), id);
        return ReqResult.success();
    }

    @Operation(summary = "查询所有临时代理配置")
    @PostMapping("/proxy/list/{sandboxId}")
    public ReqResult<List<SandboxTempLinkDto>> listAll(
            @Parameter(description = "代理ID") @PathVariable Long sandboxId) {
        List<SandboxTempLinkDto> list = sandboxProxyApplicationService.querySandboxyProxyList(RequestContext.get().getUserId(), sandboxId).stream().map(this::convertToSandboxTempLinkDto).toList();
        return ReqResult.success(list);
    }

    private SandboxTempLinkDto convertToSandboxTempLinkDto(SandboxProxyDto sandboxProxyDto) {
        SandboxTempLinkDto sandboxTempLinkDto = new SandboxTempLinkDto();
        sandboxTempLinkDto.setId(sandboxProxyDto.getId());
        if (StringUtils.isNotBlank(baseDomain)) {
            sandboxTempLinkDto.setTempLink(sandboxProxyDto.getProxyKey() + "." + baseDomain);
        } else {
            TenantConfigDto tenantConfigDto = (TenantConfigDto) RequestContext.get().getTenantConfig();
            String siteUrl = tenantConfigDto.getSiteUrl();
            siteUrl = siteUrl.endsWith("/") ? siteUrl.substring(0, siteUrl.length() - 1) : siteUrl;
            sandboxTempLinkDto.setTempLink(siteUrl + "/api/proxy/sandbox/" + sandboxProxyDto.getProxyKey());
        }
        return sandboxTempLinkDto;
    }

}
