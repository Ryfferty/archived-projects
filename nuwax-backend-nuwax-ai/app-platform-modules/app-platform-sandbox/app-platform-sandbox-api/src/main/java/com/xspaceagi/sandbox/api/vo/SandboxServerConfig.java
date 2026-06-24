package com.xspaceagi.sandbox.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SandboxServerConfig implements Serializable {

    private List<SandboxServer> sandboxServers;
    private double perUserMemoryGB;
    private int perUserCpuCores;

    @Data
    public static class SandboxServer {
        private String serverId;
        private String serverName;
        private String serverAgentUrl;
        private String serverVncUrl;
        private String serverFileUrl;
        private String serverApiKey;
        private int maxUsers;
        private double perUserMemoryGB;
        private int perUserCpuCores;
    }
}