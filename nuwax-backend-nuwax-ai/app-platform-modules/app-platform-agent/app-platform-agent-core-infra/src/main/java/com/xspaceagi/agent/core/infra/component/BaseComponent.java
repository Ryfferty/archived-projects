package com.xspaceagi.agent.core.infra.component;

import java.io.Serializable;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BaseComponent implements Serializable {

    //创建线程池
    protected static final ThreadPoolExecutor executorService = new ThreadPoolExecutor(
            5, // 核心线程数
            Runtime.getRuntime().availableProcessors() * 50, // 最大线程数，更多的用于阻塞等待
            60L, // 线程空闲时间
            TimeUnit.SECONDS, // 时间单位
            new SynchronousQueue<>()// 任务队列
    );

    //异步调用
    protected static void submit(Runnable runnable) {
        executorService.submit(runnable);
    }
}
