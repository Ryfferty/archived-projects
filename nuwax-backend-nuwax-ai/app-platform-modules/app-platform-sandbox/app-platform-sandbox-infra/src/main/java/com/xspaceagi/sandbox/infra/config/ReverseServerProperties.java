package com.xspaceagi.sandbox.infra.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 反向代理服务器配置属性
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "reverse.server")
public class ReverseServerProperties {

    /**
     * 内部服务器配置
     */
    private Inner inner = new Inner();

    /**
     * 外部服务器配置
     */
    private Outer outer = new Outer();

    /**
     * 内部服务器配置
     */
    @Data
    public static class Inner {
        /**
         * 内部服务器主机地址
         * 默认: 127.0.0.1
         */
        private String serviceHost = "127.0.0.1";

        private String bindHost = "127.0.0.1";

        /**
         * 内部服务器端口范围
         * 格式: "起始端口-结束端口"，例如 "30000-40000"
         * 默认: 30000-40000
         */
        private String ports = "30000-40000";

        /**
         * 解析端口范围
         *
         * @return 端口范围数组 [起始端口, 结束端口]
         */
        public int[] parsePortRange() {
            if (ports == null || ports.trim().isEmpty()) {
                throw new IllegalArgumentException("Port range cannot be empty");
            }

            String[] parts = ports.split("-");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid port range format, expected 'min-max', got: " + ports);
            }

            try {
                int minPort = Integer.parseInt(parts[0].trim());
                int maxPort = Integer.parseInt(parts[1].trim());

                if (minPort <= 0 || maxPort <= 0) {
                    throw new IllegalArgumentException("Port numbers must be positive");
                }

                if (minPort > maxPort) {
                    throw new IllegalArgumentException("Min port cannot be greater than max port");
                }

                return new int[]{minPort, maxPort};
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid port number format", e);
            }
        }

        /**
         * 获取起始端口
         */
        public int getMinPort() {
            return parsePortRange()[0];
        }

        /**
         * 获取结束端口
         */
        public int getMaxPort() {
            return parsePortRange()[1];
        }

        public String getServiceHost() {
            //从环境变量中获取服务器主机地址
            String envServiceHost = System.getenv("SERVICE_HOST");
            if (envServiceHost != null && !envServiceHost.trim().isEmpty()) {
                return envServiceHost;
            }
            return serviceHost;
        }

        public String getBindHost() {
            //从环境变量中获取服务器主机地址
            String envBindHost = System.getenv("BIND_HOST");
            if (envBindHost != null && !envBindHost.trim().isEmpty()) {
                return envBindHost;
            }
            return bindHost;
        }
    }

    /**
     * 外部服务器配置
     */
    @Data
    public static class Outer {
        /**
         * 外部服务器主机地址
         * 默认: 空（需要配置）
         */
        private String host = "";

        /**
         * 外部服务器端口
         * 默认: 6443
         */
        private int port = 6443;

        /**
         * 检查外部服务器是否已配置
         */
        public boolean isConfigured() {
            return host != null && !host.trim().isEmpty();
        }
    }
}
