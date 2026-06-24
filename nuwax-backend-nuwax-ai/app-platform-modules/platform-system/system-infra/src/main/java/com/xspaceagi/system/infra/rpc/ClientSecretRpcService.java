package com.xspaceagi.system.infra.rpc;

import com.xspaceagi.eco.market.sdk.reponse.ClientSecretResponse;
import com.xspaceagi.eco.market.sdk.request.ClientSecretRequest;
import com.xspaceagi.eco.market.sdk.service.IEcoMarketRpcService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ClientSecretRpcService {

    @Resource
    private IEcoMarketRpcService ecoMarketRpcService;

    public ClientSecretResponse queryClientSecret(Long tenantId) {
        ClientSecretRequest clientSecretRequest = new ClientSecretRequest(tenantId);
        ClientSecretResponse clientSecretResponse = ecoMarketRpcService.queryClientSecret(clientSecretRequest);
        return clientSecretResponse;
    }
}
