package com.xspaceagi.eco.market.spec.infra.translator.impl;

import com.xspaceagi.eco.market.domain.model.EcoMarketClientSecretModel;
import com.xspaceagi.eco.market.spec.infra.dao.entity.EcoMarketClientSecret;
import org.springframework.stereotype.Component;

import com.xspaceagi.eco.market.spec.infra.translator.IEcoMarketClientSecretTranslator;
import com.xspaceagi.system.spec.enums.YnEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EcoMarketClientSecretTranslator implements IEcoMarketClientSecretTranslator {
    @Override
    public EcoMarketClientSecretModel convertToModel(EcoMarketClientSecret entity) {
        if (entity == null) {
            return null;
        }

        EcoMarketClientSecretModel ecoMarketClientSecretModel = EcoMarketClientSecretModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret())
                .tenantId(entity.getTenantId())
                .created(entity.getCreated())
                .creatorId(entity.getCreatorId())
                .creatorName(entity.getCreatorName())
                .modified(entity.getModified())
                .build();
        return ecoMarketClientSecretModel;

    }

    @Override
    public EcoMarketClientSecret convertToEntity(EcoMarketClientSecretModel model) {
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
                .created(model.getCreated())
                .creatorId(model.getCreatorId())
                .creatorName(model.getCreatorName())
                .modified(model.getModified())
                .yn(YnEnum.Y.getKey())
                .build();
        return ecoMarketClientSecret;

    }
}
