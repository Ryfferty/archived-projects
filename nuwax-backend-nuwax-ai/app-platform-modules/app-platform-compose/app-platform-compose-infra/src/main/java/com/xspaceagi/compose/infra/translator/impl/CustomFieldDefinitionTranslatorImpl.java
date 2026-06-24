package com.xspaceagi.compose.infra.translator.impl;

import com.xspaceagi.compose.domain.model.CustomFieldDefinitionModel;
import com.xspaceagi.compose.infra.dao.entity.CustomFieldDefinition;
import com.xspaceagi.compose.infra.translator.CustomFieldDefinitionTranslator;
import com.xspaceagi.system.spec.enums.YnEnum;

import org.springframework.stereotype.Service;

@Service
public class CustomFieldDefinitionTranslatorImpl implements CustomFieldDefinitionTranslator {
    @Override
    public CustomFieldDefinitionModel convertToModel(CustomFieldDefinition entity) {
        if (entity == null) {
            return null;
        }
        CustomFieldDefinitionModel customFieldDefinitionModel = CustomFieldDefinitionModel.builder()
                .id(entity.getId())
                .systemFieldFlag(entity.getSystemFieldFlag())
                .tenantId(entity.getTenantId())
                .spaceId(entity.getSpaceId())
                .tableId(entity.getTableId())
                .fieldName(entity.getFieldName())
                .fieldDescription(entity.getFieldDescription())
                .fieldType(entity.getFieldType())
                .nullableFlag(entity.getNullableFlag())
                .defaultValue(entity.getDefaultValue())
                .uniqueFlag(entity.getUniqueFlag())
                .enabledFlag(entity.getEnabledFlag())
                .sortIndex(entity.getSortIndex())
                .created(entity.getCreated())
                .fieldStrLen(entity.getFieldStrLen())
                .creatorId(entity.getCreatorId())
                .creatorName(entity.getCreatorName())
                .modified(entity.getModified())
                .modifiedId(entity.getModifiedId())
                .modifiedName(entity.getModifiedName())
                .build();
        return customFieldDefinitionModel;

    }

    @Override
    public CustomFieldDefinition convertToEntity(CustomFieldDefinitionModel model) {
        if (model == null) {
            return null;
        }
        CustomFieldDefinition customFieldDefinition = CustomFieldDefinition.builder()
                .id(model.getId())
                .systemFieldFlag(model.getSystemFieldFlag())
                .tenantId(model.getTenantId())
                .spaceId(model.getSpaceId())
                .tableId(model.getTableId())
                .fieldName(model.getFieldName())
                .fieldDescription(model.getFieldDescription())
                .fieldType(model.getFieldType())
                .nullableFlag(model.getNullableFlag())
                .defaultValue(model.getDefaultValue())
                .uniqueFlag(model.getUniqueFlag())
                .enabledFlag(model.getEnabledFlag())
                .sortIndex(model.getSortIndex())
                .created(model.getCreated())
                .fieldStrLen(model.getFieldStrLen())
                .creatorId(model.getCreatorId())
                .creatorName(model.getCreatorName())
                .modified(model.getModified())
                .modifiedId(model.getModifiedId())
                .modifiedName(model.getModifiedName())
                .yn(YnEnum.Y.getKey())
                .build();
        return customFieldDefinition;

    }
}
