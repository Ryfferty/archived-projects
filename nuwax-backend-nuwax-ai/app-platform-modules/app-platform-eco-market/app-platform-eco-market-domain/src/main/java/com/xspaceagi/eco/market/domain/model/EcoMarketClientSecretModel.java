package com.xspaceagi.eco.market.domain.model;

import com.xspaceagi.eco.market.sdk.reponse.ClientSecretResponse;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 生态市场,客户端端配置
 * @TableName eco_market_client_secret
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EcoMarketClientSecretModel {
    /**
     * 主键id
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
     * 客户端ID,分布式唯一UUID
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

    /**
     * 创建时间
     */
    private LocalDateTime created;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 更新时间
     */
    private LocalDateTime modified;


    /**
     * 转换为客户端密钥响应
     *
     * @return 客户端密钥响应
     */
    public ClientSecretResponse toClientSecretResponse() {
        return ClientSecretResponse.builder()
                .id(id)
                .name(name)
                .description(description)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .created(created)
                .build();
    }

}