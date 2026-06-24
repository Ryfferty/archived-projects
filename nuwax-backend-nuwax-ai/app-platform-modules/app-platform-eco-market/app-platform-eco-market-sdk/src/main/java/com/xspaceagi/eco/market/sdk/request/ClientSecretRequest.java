package com.xspaceagi.eco.market.sdk.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 客户端密钥请求参数
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientSecretRequest {

    /**
     * 租户ID
     */
    private Long tenantId;

}
