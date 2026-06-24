package com.xspaceagi.system.web.controller;

import com.xspaceagi.system.application.service.SysApplicationService;
import com.xspaceagi.system.application.service.SysUserPermissionCacheService;
import com.xspaceagi.system.spec.dto.ReqResult;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/sys")
public class SysController {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Resource
    private SysApplicationService sysApplicationService;
    @Resource
    private SysUserPermissionCacheService sysUserPermissionCacheService;

    /**
     * 获取系统启动时间，排查问题使用
     */
    @GetMapping(value = "/v", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReqResult<String> startTime() {
        LocalDateTime serverStartupTime = sysApplicationService.getServerStartupTime();
        return ReqResult.success(serverStartupTime != null ? serverStartupTime.format(FORMAT) : null);
    }

    @GetMapping(value = "/permission-cache-time", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReqResult<String> permissionCacheTime() {
        Long timestamp = sysUserPermissionCacheService.getPermissionLatestCacheTime();
        if (timestamp == null) {
            return ReqResult.success(null);
        }
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        return ReqResult.success(dateTime.format(FORMAT));
    }

}