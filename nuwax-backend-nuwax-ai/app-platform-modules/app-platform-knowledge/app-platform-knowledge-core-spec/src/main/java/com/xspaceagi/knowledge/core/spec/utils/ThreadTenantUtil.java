package com.xspaceagi.knowledge.core.spec.utils;

import com.xspaceagi.system.spec.tenant.thread.TenantThreadPoolExecutor;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

/**
 * 公共线程池工具
 */
@Slf4j
@Component
public class ThreadTenantUtil {

    /**
     * 分段,重试等业务的线程池
     */
    private TenantThreadPoolExecutor executor;
    /**
     * 知识库其他的业务任务,如更新文件大小
     */
    private TenantThreadPoolExecutor scheduledExecutorService;
    /**
     * 分段使用独立线程执行
     */
    private TenantThreadPoolExecutor rawExecutor;

    @PostConstruct
    public void init() {
        // 原始线程池
        executor = new TenantThreadPoolExecutor(Executors.newFixedThreadPool(3));

        scheduledExecutorService = new TenantThreadPoolExecutor(Executors.newScheduledThreadPool(5));

        rawExecutor = new TenantThreadPoolExecutor(Executors.newFixedThreadPool(20));

        log.info("初始化线程池[ThreadTenantUtil]");
    }

    @PreDestroy
    public void destroy() {
        log.info("服务停止运行,关闭线程池");
        if (executor != null) {
            executor.shutdownNow();
        }

        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdownNow();
        }

        if (rawExecutor != null) {
            rawExecutor.shutdownNow();
        }

    }

    /**
     * 获取通用线程池
     *
     * @return
     */
    public TenantThreadPoolExecutor obtainCommonExecutor() {
        return this.executor;
    }

    /**
     * 其他常规的知识库业务
     *
     * @return
     */
    public TenantThreadPoolExecutor obtainOtherScheduledExecutor() {
        return this.scheduledExecutorService;
    }

    /**
     * 分段使用独立线程执行
     * 
     * @return
     */
    public TenantThreadPoolExecutor obtainRawExecutor() {
        return this.rawExecutor;
    }

}
