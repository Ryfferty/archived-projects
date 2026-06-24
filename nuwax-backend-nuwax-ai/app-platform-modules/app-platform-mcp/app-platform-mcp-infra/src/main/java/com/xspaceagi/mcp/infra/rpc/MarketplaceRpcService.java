package com.xspaceagi.mcp.infra.rpc;

import com.xspaceagi.eco.market.sdk.reponse.ClientSecretResponse;
import com.xspaceagi.eco.market.sdk.request.ClientSecretRequest;
import com.xspaceagi.eco.market.sdk.service.IEcoMarketRpcService;
import com.xspaceagi.system.spec.cache.SimpleJvmHashCache;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class MarketplaceRpcService {

    @Resource
    private IEcoMarketRpcService ecoMarketRpcService;


    public ClientSecretResponse queryClientSecret(ClientSecretRequest request) {
        Object clientSecret = SimpleJvmHashCache.getHash("client_secret", request.getTenantId().toString());
        if (clientSecret != null) {
            return (ClientSecretResponse) clientSecret;
        }
        ClientSecretResponse clientSecretResponse = ecoMarketRpcService.queryClientSecret(request);
        if (clientSecretResponse != null){
            SimpleJvmHashCache.putHash("client_secret", request.getTenantId().toString(), clientSecretResponse, 7200);
            return clientSecretResponse;
        }
        return null;
    }
}
