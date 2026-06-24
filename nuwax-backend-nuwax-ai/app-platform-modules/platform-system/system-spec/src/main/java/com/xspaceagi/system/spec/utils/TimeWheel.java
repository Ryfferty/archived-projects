package com.xspaceagi.system.spec.utils;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * 时间轮定时器
 * 用于处理延迟任务的调度，支持最大180秒的延迟
 */
@Slf4j
@Component
public class TimeWheel {
    private static final int TICK_DURATION = 1; // 每个tick的时长（秒）
    private static final int WHEEL_SIZE = 180;  // 时间轮大小，支持最大180秒延迟
    private static final int MAX_DELAY_SECONDS = TICK_DURATION * WHEEL_SIZE; // 180秒

    // 静态引用，指向 Spring 管理的实例
    private static volatile TimeWheel instance;

    private final AtomicInteger currentTick = new AtomicInteger(0);
    private ScheduledExecutorService scheduler;
    private ExecutorService taskExecutor;
    private final ConcurrentHashMap<Integer, ConcurrentLinkedQueue<Consumer<?>>> wheel = new ConcurrentHashMap<>(WHEEL_SIZE);

    /**
     * 获取 TimeWheel 实例（由 Spring 管理）
     *
     * @throws IllegalStateException 如果 Spring 尚未初始化 TimeWheel
     */
    public static TimeWheel getInstance() {
        if (instance == null) {
            throw new IllegalStateException(
                "TimeWheel is not initialized. Please ensure Spring context is loaded."
            );
        }
        return instance;
    }

    @PostConstruct
    public void init() {
        // 设置静态引用，使 getInstance() 可以访问 Spring 管理的实例
        TimeWheel.instance = this;

        // 初始化时间轮槽
        for (int i = 0; i < WHEEL_SIZE; i++) {
            wheel.put(i, new ConcurrentLinkedQueue<>());
        }

        // 创建调度线程池（单线程负责tick推进）
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "TimeWheel-Scheduler");
            thread.setDaemon(false);
            return thread;
        });

        // 创建任务执行线程池（异步执行回调任务）
        this.taskExecutor = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors(),
            r -> {
                Thread thread = new Thread(r, "TimeWheel-Task-Executor");
                thread.setDaemon(false);
                return thread;
            }
        );

        // 启动时间轮
        start();
        log.info("TimeWheel initialized with tickDuration={}s, wheelSize={}", TICK_DURATION, WHEEL_SIZE);
    }

    private void start() {
        scheduler.scheduleAtFixedRate(
            this::advance,
            TICK_DURATION,
            TICK_DURATION,
            TimeUnit.SECONDS
        );
    }

    /**
     * 时间轮推进逻辑
     */
    private void advance() {
        // 使用 Math.floorMod 处理负数情况，避免整数溢出问题
        int tick = Math.floorMod(currentTick.getAndIncrement(), WHEEL_SIZE);
        ConcurrentLinkedQueue<Consumer<?>> tasks = wheel.get(tick);

        if (tasks == null || tasks.isEmpty()) {
            return;
        }

        Consumer<?> task;
        while ((task = tasks.poll()) != null) {
            executeTask(task);
        }
    }

    /**
     * 异步执行任务，并处理异常
     */
    private void executeTask(Consumer<?> task) {
        try {
            CompletableFuture
                .runAsync(() -> task.accept(null), taskExecutor)
                .exceptionally(ex -> {
                    log.error("Error executing time wheel task: {}", ex.getMessage(), ex);
                    return null;
                });
        } catch (RejectedExecutionException e) {
            log.error("Task executor rejected task, possibly shutting down: {}", e.getMessage());
        } catch (Throwable e) {
            log.error("Unexpected error submitting time wheel task: {}", e.getMessage(), e);
        }
    }

    /**
     * 调度延迟任务
     *
     * @param callback 回调函数
     * @param delaySeconds 延迟时间（秒），必须大于0且小于等于180
     * @throws IllegalArgumentException 如果延迟时间超出范围
     */
    public <T> void schedule(Consumer<T> callback, int delaySeconds) {
        if (callback == null) {
            throw new IllegalArgumentException("Callback cannot be null");
        }
        if (delaySeconds <= 0) {
            throw new IllegalArgumentException("Delay must be positive, got: " + delaySeconds);
        }
        if (delaySeconds > MAX_DELAY_SECONDS) {
            throw new IllegalArgumentException(
                String.format("Delay exceeds maximum allowed delay of %d seconds, got: %d",
                    MAX_DELAY_SECONDS, delaySeconds)
            );
        }

        int ticks = delaySeconds / TICK_DURATION;
        int slot = Math.floorMod(currentTick.get() + ticks, WHEEL_SIZE);

        ConcurrentLinkedQueue<Consumer<?>> slotQueue = wheel.get(slot);
        if (slotQueue != null) {
            slotQueue.add(callback);
        } else {
            log.error("Invalid slot calculated: {}, this should never happen", slot);
        }
    }

    /**
     * 获取当前支持的延迟上限
     */
    public static int getMaxDelaySeconds() {
        return MAX_DELAY_SECONDS;
    }

    @PreDestroy
    public void shutdown() {
        log.info("Shutting down TimeWheel...");

        // 清除静态引用
        TimeWheel.instance = null;

        // 关闭调度器
        shutdownExecutor(scheduler, "Scheduler");

        // 关闭任务执行器
        shutdownExecutor(taskExecutor, "Task-Executor");

        log.info("TimeWheel shutdown completed");
    }

    private void shutdownExecutor(ExecutorService executor, String name) {
        if (executor == null || executor.isShutdown()) {
            return;
        }

        try {
            // 停止接受新任务
            executor.shutdown();

            // 等待现有任务完成，最多等待30秒
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                log.warn("{} did not terminate in time, forcing shutdown", name);
                executor.shutdownNow();

                // 再等待10秒
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    log.error("{} failed to terminate properly", name);
                }
            }
        } catch (InterruptedException e) {
            log.warn("Shutdown interrupted for {}, forcing immediate shutdown", name);
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}