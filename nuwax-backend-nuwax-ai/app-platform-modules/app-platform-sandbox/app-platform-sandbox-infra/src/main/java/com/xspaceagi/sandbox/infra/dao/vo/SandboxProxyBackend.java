package com.xspaceagi.sandbox.infra.dao.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 临时代理实体类
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SandboxProxyBackend implements Serializable {

    private Long sandboxId;

    private String proxyKey;

    private String backendHost;

    private Integer backendPort;

    private String sandboxConfigKey;
}