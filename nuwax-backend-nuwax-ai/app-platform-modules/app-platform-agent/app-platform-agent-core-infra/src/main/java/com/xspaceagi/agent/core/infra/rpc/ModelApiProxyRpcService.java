package com.xspaceagi.agent.core.infra.rpc;

import com.xspaceagi.modelproxy.sdk.service.IModelApiProxyConfigService;
import com.xspaceagi.modelproxy.sdk.service.dto.BackendModelDto;
import com.xspaceagi.modelproxy.sdk.service.dto.FrontendModelDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ModelApiProxyRpcService {

    @Resource
    private IModelApiProxyConfigService modelApiProxyConfigService;

    public FrontendModelDto generateUserFrontendModelConfig(Long tenantId, Long userId, Long agentId, BackendModelDto backendModel, String siteUrl) {
        return modelApiProxyConfigService.generateUserFrontendModelConfig(tenantId, userId, agentId, backendModel, siteUrl);
    }
}
