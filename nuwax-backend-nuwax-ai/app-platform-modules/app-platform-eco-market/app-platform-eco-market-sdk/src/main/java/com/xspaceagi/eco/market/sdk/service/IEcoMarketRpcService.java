package com.xspaceagi.eco.market.sdk.service;

import com.xspaceagi.eco.market.sdk.reponse.ClientSecretResponse;
import com.xspaceagi.eco.market.sdk.request.ClientSecretRequest;

/**
 * 生态市场RPC服务
 */
public interface IEcoMarketRpcService {

    /**
     * 根据租户id，查询客户端密钥
     *
     * @param request 请求参数
     * @return 客户端密钥
     */
    ClientSecretResponse queryClientSecret(ClientSecretRequest request);

}
