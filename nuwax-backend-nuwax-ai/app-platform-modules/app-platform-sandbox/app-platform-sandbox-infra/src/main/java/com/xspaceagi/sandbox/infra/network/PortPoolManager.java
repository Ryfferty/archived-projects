package com.xspaceagi.sandbox.infra.network;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 端口池管理器
 *
 * 功能：
 * 1. 管理指定范围的端口池
 * 2. 支持端口借用和归还
 * 3. 归还的端口需要等待冷却时间（默认1分钟）后才能再次被使用
 * 4. 线程安全
 */
@Slf4j
public class PortPoolManager {

    /**
     * 端口信息
     */
    private static class PortInfo {
        private final int port;
        private long allocateTime;      // 分配时间
        private long releaseTime;        // 释放时间（用于冷却）
        private boolean inUse;           // 是否在使用中
        private boolean cooling;          // 是否在冷却中

        public PortInfo(int port) {
            this.port = port;
            this.inUse = false;
            this.cooling = false;
        }

        public void allocate() {
            this.inUse = true;
            this.allocateTime = System.currentTimeMillis();
            this.releaseTime = 0;
        }

        public void release() {
            this.inUse = false;
            this.cooling = true;
            this.releaseTime = System.currentTimeMillis();
        }

        public boolean isAvailable(long coolDownMillis) {
            if (inUse) {
                return false;
            }
            if (cooling) {
                return System.currentTimeMillis() - releaseTime >= coolDownMillis;
            }
            return true;
        }

        public void reset() {
            this.inUse = false;
            this.cooling = false;
            this.releaseTime = 0;
        }

        public int getPort() {
            return port;
        }

        public long getAllocateTime() {
            return allocateTime;
        }

        public long getReleaseTime() {
            return releaseTime;
        }
    }

    private final int minPort;
    private final int maxPort;
    private final long coolDownMillis;  // 冷却时间（毫秒）

    // 端口信息映射
    private final Map<Integer, PortInfo> portInfoMap;

    // 可用端口队列（线程安全）
    private final BlockingQueue<PortInfo> availablePorts;

    // 定时检查冷却端口
    private final ScheduledExecutorService scheduler;

    /**
     * 构造函数
     *
     * @param minPort 起始端口（包含）
     * @param maxPort 结束端口（包含）
     * @param coolDownSeconds 冷却时间（秒）
     */
    public PortPoolManager(int minPort, int maxPort, int coolDownSeconds) {
        if (minPort <= 0 || maxPort <= 0 || minPort > maxPort) {
            throw new IllegalArgumentException("Invalid port range");
        }
        if (coolDownSeconds <= 0) {
            throw new IllegalArgumentException("Cool down time must be positive");
        }

        this.minPort = minPort;
        this.maxPort = maxPort;
        this.coolDownMillis = coolDownSeconds * 1000L;

        this.portInfoMap = new ConcurrentHashMap<>();
        this.availablePorts = new LinkedBlockingQueue<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "port-pool-cooler");
            thread.setDaemon(true);
            return thread;
        });

        initializePortPool();
        startCoolDownChecker();
    }

    /**
     * 默认构造函数（1分钟冷却时间）
     *
     * @param minPort 起始端口（包含）
     * @param maxPort 结束端口（包含）
     */
    public PortPoolManager(int minPort, int maxPort) {
        this(minPort, maxPort, 60);
    }

    /**
     * 初始化端口池
     */
    private void initializePortPool() {
        log.info("Initializing port pool: {} -> {}, cool down: {}ms", minPort, maxPort, coolDownMillis);

        for (int port = minPort; port <= maxPort; port++) {
            PortInfo portInfo = new PortInfo(port);
            portInfoMap.put(port, portInfo);
            availablePorts.offer(portInfo);
        }

        log.info("Port pool initialized with {} ports", maxPort - minPort + 1);
    }

    /**
     * 启动冷却端口检查器
     */
    private void startCoolDownChecker() {
        // 每10秒检查一次冷却端口
        scheduler.scheduleAtFixedRate(() -> {
            try {
                checkAndReturnCoolingPorts();
            } catch (Exception e) {
                log.error("Error checking cooling ports", e);
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    /**
     * 检查并返回已冷却的端口
     */
    private void checkAndReturnCoolingPorts() {
        int returnedCount = 0;

        for (PortInfo portInfo : portInfoMap.values()) {
            if (portInfo.cooling && portInfo.isAvailable(0)) {
                // 冷却完成，重置状态并放回可用队列
                portInfo.reset();
                if (availablePorts.offer(portInfo)) {
                    returnedCount++;
                    log.debug("Port {} returned to pool after cooling", portInfo.getPort());
                }
            }
        }

        if (returnedCount > 0) {
            log.info("Returned {} ports to pool after cooling", returnedCount);
        }
    }

    /**
     * 借用端口（阻塞等待）
     *
     * @return 可用的端口
     * @throws InterruptedException 如果等待被中断
     */
    public int borrow() throws Exception {
        return borrow(0, TimeUnit.MILLISECONDS);
    }

    /**
     * 借用端口（带超时）
     *
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return 可用的端口
     * @throws InterruptedException 如果等待被中断
     */
    public int borrow(long timeout, TimeUnit unit) throws Exception {
        PortInfo portInfo;
        if (timeout > 0) {
            portInfo = availablePorts.poll(timeout, unit);
        } else {
            portInfo = availablePorts.take();
        }

        if (portInfo == null) {
            throw new TimeoutException("No available ports in pool");
        }

        portInfo.allocate();
        log.debug("Port {} borrowed", portInfo.getPort());

        return portInfo.getPort();
    }

    /**
     * 归还端口
     *
     * @param port 要归还的端口
     * @throws IllegalArgumentException 如果端口不在池中
     */
    public void release(int port) {
        PortInfo portInfo = portInfoMap.get(port);
        if (portInfo == null) {
            throw new IllegalArgumentException("Port " + port + " is not managed by this pool");
        }

        if (!portInfo.inUse) {
            log.warn("Port {} is not in use, ignore release", port);
            return;
        }

        portInfo.release();
        log.debug("Port {} released, entering cooling period", port);
    }

    /**
     * 强制归还端口（不进入冷却期）
     *
     * @param port 要归还的端口
     */
    public void releaseImmediately(int port) {
        PortInfo portInfo = portInfoMap.get(port);
        if (portInfo == null) {
            throw new IllegalArgumentException("Port " + port + " is not managed by this pool");
        }

        boolean wasInUse = portInfo.inUse;
        portInfo.reset();

        if (wasInUse) {
            // 如果之前在使用中，放回可用队列
            availablePorts.offer(portInfo);
            log.debug("Port {} released immediately (no cooling)", port);
        }
    }

    /**
     * 获取池中的端口总数
     *
     * @return 端口总数
     */
    public int getTotalPorts() {
        return portInfoMap.size();
    }

    /**
     * 获取可用端口数
     *
     * @return 可用端口数
     */
    public int getAvailablePortCount() {
        return availablePorts.size();
    }

    /**
     * 获取使用中的端口数
     *
     * @return 使用中的端口数
     */
    public int getInUsePortCount() {
        int count = 0;
        for (PortInfo portInfo : portInfoMap.values()) {
            if (portInfo.inUse) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取冷却中的端口数
     *
     * @return 冷却中的端口数
     */
    public int getCoolingPortCount() {
        int count = 0;
        for (PortInfo portInfo : portInfoMap.values()) {
            if (portInfo.cooling && !portInfo.inUse) {
                count++;
            }
        }
        return count;
    }

    /**
     * 检查端口是否在池中
     *
     * @param port 端口
     * @return 是否在池中
     */
    public boolean containsPort(int port) {
        return portInfoMap.containsKey(port);
    }

    /**
     * 检查端口是否可用
     *
     * @param port 端口
     * @return 是否可用
     */
    public boolean isAvailable(int port) {
        PortInfo portInfo = portInfoMap.get(port);
        return portInfo != null && portInfo.isAvailable(coolDownMillis);
    }

    /**
     * 获取端口状态
     *
     * @param port 端口
     * @return 状态字符串：AVAILABLE, IN_USE, COOLING, NOT_FOUND
     */
    public String getPortStatus(int port) {
        PortInfo portInfo = portInfoMap.get(port);
        if (portInfo == null) {
            return "NOT_FOUND";
        }
        if (portInfo.inUse) {
            return "IN_USE";
        }
        if (portInfo.cooling) {
            return "COOLING";
        }
        return "AVAILABLE";
    }

    /**
     * 关闭端口池
     */
    public void shutdown() {
        log.info("Shutting down port pool manager");
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("Port pool manager shutdown complete");
    }

    /**
     * 获取端口池统计信息
     *
     * @return 统计信息
     */
    public PortPoolStats getStats() {
        int inUse = getInUsePortCount();
        int available = getAvailablePortCount();
        int cooling = getCoolingPortCount();

        return new PortPoolStats(
            minPort,
            maxPort,
            getTotalPorts(),
            available,
            inUse,
            cooling,
            coolDownMillis / 1000
        );
    }

    /**
     * 端口池统计信息
     */
    public record PortPoolStats(
        int minPort,
        int maxPort,
        int totalPorts,
        int availablePorts,
        int inUsePorts,
        int coolingPorts,
        long coolDownSeconds
    ) {
        @Override
        public String toString() {
            return String.format(
                "PortPoolStats{range=%d-%d, total=%d, available=%d, inUse=%d, cooling=%d, coolDown=%ds}",
                minPort, maxPort, totalPorts, availablePorts, inUsePorts, coolingPorts, coolDownSeconds
            );
        }
    }
}
