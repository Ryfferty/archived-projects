package com.xspaceagi.memory.spec;

/**
 * Memory模块常量定义
 */
public class MemoryConstants {

    /**
     * 记忆类型
     */
    public static final class MemoryType {
        public static final String SHORT_TERM = "short_term";
        public static final String LONG_TERM = "long_term";
        public static final String WORKING = "working";
    }

    /**
     * 记忆状态
     */
    public static final class MemoryStatus {
        public static final String ACTIVE = "active";
        public static final String ARCHIVED = "archived";
        public static final String DELETED = "deleted";
    }

    /**
     * 默认配置
     */
    public static final class DefaultConfig {
        public static final int MAX_SHORT_TERM_MEMORY_SIZE = 100;
        public static final int MAX_WORKING_MEMORY_SIZE = 50;
        public static final long LONG_TERM_MEMORY_THRESHOLD = 7 * 24 * 60 * 60 * 1000L; // 7天
    }

    private MemoryConstants() {
        // 私有构造函数，防止实例化
    }
}
