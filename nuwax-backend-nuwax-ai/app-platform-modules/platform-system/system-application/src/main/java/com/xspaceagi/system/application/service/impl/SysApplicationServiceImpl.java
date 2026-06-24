package com.xspaceagi.system.application.service.impl;

import com.xspaceagi.system.application.component.ServerStartupTimeHolder;
import com.xspaceagi.system.application.service.SysApplicationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SysApplicationServiceImpl implements SysApplicationService {

    @Resource
    private ServerStartupTimeHolder serverStartupTimeHolder;

    @Override
    public LocalDateTime getServerStartupTime() {
        return serverStartupTimeHolder.getServerStartupTime();
    }
}
