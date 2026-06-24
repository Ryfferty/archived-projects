package com.xspaceagi.compose.infra.translator.impl;

import com.xspaceagi.compose.domain.model.CustomTableDefinitionModel;
import com.xspaceagi.compose.infra.dao.entity.CustomTableDefinition;
import com.xspaceagi.compose.infra.translator.CustomTableDefinitionTranslator;
import com.xspaceagi.system.spec.enums.YnEnum;

import org.springframework.stereotype.Service;

@Service
public class CustomTableDefinitionTranslatorImpl implements CustomTableDefinitionTranslator {
    @Override
    public CustomTableDefinitionModel convertToModel(CustomTableDefinition entity) {
        if (entity == null) {
            return null;
        }
        CustomTableDefinitionModel customTableDefinitionModel = new CustomTableDefinitionModel();
        customTableDefinitionModel.setId(entity.getId());
        customTableDefinitionModel.setTenantId(entity.getTenantId());
        customTableDefinitionModel.setSpaceId(entity.getSpaceId());
        customTableDefinitionModel.setIcon(entity.getIcon());
        customTableDefinitionModel.setTableName(entity.getTableName());
        customTableDefinitionModel.setTableDescription(entity.getTableDescription());
        customTableDefinitionModel.setDorisDatabase(entity.getDorisDatabase());
        customTableDefinitionModel.setDorisTable(entity.getDorisTable());
        customTableDefinitionModel.setStatus(entity.getStatus());
        customTableDefinitionModel.setCreated(entity.getCreated());
        customTableDefinitionModel.setCreatorId(entity.getCreatorId());
        customTableDefinitionModel.setCreatorName(entity.getCreatorName());
        customTableDefinitionModel.setModified(entity.getModified());
        customTableDefinitionModel.setModifiedId(entity.getModifiedId());
        customTableDefinitionModel.setModifiedName(entity.getModifiedName());
        return customTableDefinitionModel;

    }

    @Override
    public CustomTableDefinition convertToEntity(CustomTableDefinitionModel model) {
        if (model == null) {
            return null;
        }
        CustomTableDefinition customTableDefinition = new CustomTableDefinition();
        customTableDefinition.setId(model.getId());
        customTableDefinition.setTenantId(model.getTenantId());
        customTableDefinition.setSpaceId(model.getSpaceId());
        customTableDefinition.setIcon(model.getIcon());
        customTableDefinition.setTableName(model.getTableName());
        customTableDefinition.setTableDescription(model.getTableDescription());
        customTableDefinition.setDorisDatabase(model.getDorisDatabase());
        customTableDefinition.setDorisTable(model.getDorisTable());
        customTableDefinition.setStatus(model.getStatus());
        customTableDefinition.setCreated(model.getCreated());
        customTableDefinition.setCreatorId(model.getCreatorId());
        customTableDefinition.setCreatorName(model.getCreatorName());
        customTableDefinition.setModified(model.getModified());
        customTableDefinition.setModifiedId(model.getModifiedId());
        customTableDefinition.setModifiedName(model.getModifiedName());
        customTableDefinition.setYn(YnEnum.Y.getKey());
        return customTableDefinition;

    }
}
