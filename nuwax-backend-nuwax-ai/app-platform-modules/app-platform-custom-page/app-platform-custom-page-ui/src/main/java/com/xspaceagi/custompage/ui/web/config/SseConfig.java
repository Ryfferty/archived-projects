package com.xspaceagi.custompage.ui.web.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * SSE配置类
 * 用于管理SSE连接
 */
@Configuration
public class SseConfig {

    /**
     * SSE连接管理器
     */
    @Bean
    public SseConnectionManager sseConnectionManager() {
        return new SseConnectionManager();
    }

    /**
     * SSE连接管理器，依赖Spring SseEmitter的内置断开检测机制
     */
    public static class SseConnectionManager {
        private final ConcurrentHashMap<String, SseEmitter> connections = new ConcurrentHashMap<>();

        /**
         * 添加SSE连接
         */
        public void addConnection(String sessionId, SseEmitter emitter) {
            connections.put(sessionId, emitter);

            // 设置连接完成和错误回调，依赖Spring的内置机制
            emitter.onCompletion(() -> {
                connections.remove(sessionId);
            });

            emitter.onTimeout(() -> {
                connections.remove(sessionId);
            });

            emitter.onError((throwable) -> {
                connections.remove(sessionId);
            });
        }

        /**
         * 移除SSE连接
         */
        public void removeConnection(String sessionId) {
            SseEmitter emitter = connections.remove(sessionId);
            if (emitter != null) {
                try {
                    emitter.complete();
                } catch (Exception e) {
                    // 忽略完成时的异常
                }
            }
        }

        /**
         * 获取连接数量
         */
        public int getConnectionCount() {
            return connections.size();
        }

        /**
         * 关闭所有连接
         */
        public void shutdown() {
            connections.values().forEach(emitter -> {
                try {
                    emitter.complete();
                } catch (Exception e) {
                    // 忽略异常
                }
            });
            connections.clear();
        }
    }
}
