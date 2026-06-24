
package com.xspaceagi.eco.market.spec.app.assembler;

import com.xspaceagi.eco.market.sdk.model.ClientSecretDTO;
import com.xspaceagi.eco.market.spec.infra.dao.entity.EcoMarketClientSecret;
import com.xspaceagi.system.spec.enums.YnEnum;
import com.xspaceagi.system.infra.dao.ICommonTranslator;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EcoMarketClientSecretApplicationTranslator implements ICommonTranslator<ClientSecretDTO, EcoMarketClientSecret> {

    @Override
    public ClientSecretDTO convertToModel(EcoMarketClientSecret entity) {
        if (entity == null) {
            return null;
        }

        ClientSecretDTO clientSecretDTO = ClientSecretDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .tenantId(entity.getTenantId())
                .build();
        return clientSecretDTO;


    }

    @Override
    public EcoMarketClientSecret convertToEntity(ClientSecretDTO model) {
        if (model == null) {
            return null;
        }

        EcoMarketClientSecret ecoMarketClientSecret = EcoMarketClientSecret.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .clientId(model.getClientId())
                .clientSecret(model.getClientSecret())
                .tenantId(model.getTenantId())
                .created(LocalDateTime.now())
                .creatorId(null)
                .creatorName(null)
                .modified(null)
                .yn(YnEnum.Y.getKey())
                .build();
        return ecoMarketClientSecret;

    }

}