package com.xspaceagi.eco.market.sdk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户端密钥DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientSecretDTO {

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 租户ID
     */
    private Long tenantId;

}