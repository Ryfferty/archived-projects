package com.xspaceagi.custompage.infra.translator.impl;

import org.springframework.stereotype.Component;

import com.xspaceagi.custompage.domain.model.CustomPageConversationModel;
import com.xspaceagi.custompage.infra.dao.entity.CustomPageConversation;
import com.xspaceagi.custompage.infra.translator.ICustomPageConversationTranslator;

@Component
public class CustomPageConversationTranslatorImpl implements ICustomPageConversationTranslator {

    @Override
    public CustomPageConversationModel convertToModel(CustomPageConversation entity) {
        if (entity == null) {
            return null;
        }

        CustomPageConversationModel model = new CustomPageConversationModel();
        model.setId(entity.getId());
        model.setProjectId(entity.getProjectId());
        model.setTopic(entity.getTopic());
        model.setContent(entity.getContent());
        model.setRole(entity.getRole());
        model.setSessionId(entity.getSessionId());
        model.setRequestId(entity.getRequestId());
        model.setTenantId(entity.getTenantId());
        model.setSpaceId(entity.getSpaceId());
        model.setCreated(entity.getCreated());
        model.setCreatorId(entity.getCreatorId());
        model.setCreatorName(entity.getCreatorName());
        model.setModified(entity.getModified());
        model.setModifiedId(entity.getModifiedId());
        model.setModifiedName(entity.getModifiedName());
        model.setYn(entity.getYn());

        return model;
    }

    @Override
    public CustomPageConversation convertToEntity(CustomPageConversationModel model) {
        if (model == null) {
            return null;
        }

        CustomPageConversation entity = new CustomPageConversation();
        entity.setId(model.getId());
        entity.setProjectId(model.getProjectId());
        entity.setTopic(model.getTopic());
        entity.setContent(model.getContent());
        entity.setRole(model.getRole());
        entity.setSessionId(model.getSessionId());
        entity.setRequestId(model.getRequestId());
        entity.setTenantId(model.getTenantId());
        entity.setSpaceId(model.getSpaceId());
        entity.setCreated(model.getCreated());
        entity.setCreatorId(model.getCreatorId());
        entity.setCreatorName(model.getCreatorName());
        entity.setModified(model.getModified());
        entity.setModifiedId(model.getModifiedId());
        entity.setModifiedName(model.getModifiedName());
        entity.setYn(model.getYn());

        return entity;
    }
}
