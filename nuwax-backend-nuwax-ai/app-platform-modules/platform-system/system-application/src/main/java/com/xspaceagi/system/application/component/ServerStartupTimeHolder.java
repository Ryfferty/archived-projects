package com.xspaceagi.system.application.component;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 记录服务启动时间，在应用启动完成时写入
 */
@Component
public class ServerStartupTimeHolder {

    private volatile LocalDateTime serverStartupTime;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady(ApplicationReadyEvent event) {
        this.serverStartupTime = LocalDateTime.now();
    }

    public LocalDateTime getServerStartupTime() {
        return serverStartupTime;
    }
}
