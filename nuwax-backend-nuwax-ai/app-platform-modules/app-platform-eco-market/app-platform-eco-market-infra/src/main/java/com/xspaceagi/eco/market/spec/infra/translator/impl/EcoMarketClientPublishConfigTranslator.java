package com.xspaceagi.eco.market.spec.infra.translator.impl;

import org.springframework.stereotype.Component;

import com.xspaceagi.eco.market.domain.model.EcoMarketClientPublishConfigModel;
import com.xspaceagi.eco.market.spec.infra.dao.entity.EcoMarketClientPublishConfig;
import com.xspaceagi.eco.market.spec.infra.translator.IEcoMarketClientPublishConfigTranslator;
import com.xspaceagi.system.spec.enums.YnEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EcoMarketClientPublishConfigTranslator implements IEcoMarketClientPublishConfigTranslator {

    @Override
    public EcoMarketClientPublishConfigModel convertToModel(EcoMarketClientPublishConfig entity) {
        if (entity == null) {
            return null;
        }

        return EcoMarketClientPublishConfigModel
                .builder()
                .id(entity.getId())
                .uid(entity.getUid())
                .name(entity.getName())
                .description(entity.getDescription())
                .dataType(entity.getDataType())
                .targetType(entity.getTargetType())
                .targetSubType(entity.getTargetSubType())
                .targetId(entity.getTargetId())
                .categoryCode(entity.getCategoryCode())
                .categoryName(entity.getCategoryName())
                .ownedFlag(entity.getOwnedFlag())
                .shareStatus(entity.getShareStatus())
                .useStatus(entity.getUseStatus())
                .publishTime(entity.getPublishTime())
                .offlineTime(entity.getOfflineTime())
                .versionNumber(entity.getVersionNumber())
                .author(entity.getAuthor())
                .publishDoc(entity.getPublishDoc())
                .configParamJson(entity.getConfigParamJson())
                .configJson(entity.getConfigJson())
                .icon(entity.getIcon())
                .approveMessage(entity.getApproveMessage())
                .tenantEnabled(entity.getTenantEnabled())
                .tenantId(entity.getTenantId())
                .createClientId(entity.getCreateClientId())
                .created(entity.getCreated())
                .creatorId(entity.getCreatorId())
                .creatorName(entity.getCreatorName())
                .modified(entity.getModified())
                .modifiedId(entity.getModifiedId())
                .modifiedName(entity.getModifiedName())
                .pageZipUrl(entity.getPageZipUrl())
                .build();

    }

    @Override
    public EcoMarketClientPublishConfig convertToEntity(EcoMarketClientPublishConfigModel model) {
        if (model == null) {
            return null;
        }

        return EcoMarketClientPublishConfig.builder()
                .id(model.getId())
                .uid(model.getUid())
                .name(model.getName())
                .description(model.getDescription())
                .dataType(model.getDataType())
                .targetType(model.getTargetType())
                .targetSubType(model.getTargetSubType())
                .targetId(model.getTargetId())
                .categoryCode(model.getCategoryCode())
                .categoryName(model.getCategoryName())
                .ownedFlag(model.getOwnedFlag())
                .shareStatus(model.getShareStatus())
                .useStatus(model.getUseStatus())
                .publishTime(model.getPublishTime())
                .offlineTime(model.getOfflineTime())
                .versionNumber(model.getVersionNumber())
                .author(model.getAuthor())
                .publishDoc(model.getPublishDoc())
                .configParamJson(model.getConfigParamJson())
                .configJson(model.getConfigJson())
                .icon(model.getIcon())
                .approveMessage(model.getApproveMessage())
                .tenantEnabled(model.getTenantEnabled())
                .tenantId(model.getTenantId())
                .createClientId(model.getCreateClientId())
                .created(model.getCreated())
                .creatorId(model.getCreatorId())
                .creatorName(model.getCreatorName())
                .modified(model.getModified())
                .modifiedId(model.getModifiedId())
                .modifiedName(model.getModifiedName())
                .yn(YnEnum.Y.getKey())
                .pageZipUrl(model.getPageZipUrl())
                .build();
    }
}
