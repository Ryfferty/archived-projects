package com.xspaceagi.custompage.infra.translator.impl;

import org.springframework.stereotype.Component;

import com.xspaceagi.custompage.domain.model.CustomPageDomainModel;
import com.xspaceagi.custompage.infra.dao.entity.CustomPageDomain;
import com.xspaceagi.custompage.infra.translator.ICustomPageDomainTranslator;

@Component
public class CustomPageDomainTranslatorImpl implements ICustomPageDomainTranslator {

    @Override
    public CustomPageDomainModel convertToModel(CustomPageDomain entity) {
        if (entity == null) {
            return null;
        }
        CustomPageDomainModel model = new CustomPageDomainModel();
        model.setId(entity.getId());
        model.setTenantId(entity.getTenantId());
        model.setProjectId(entity.getProjectId());
        model.setDomain(entity.getDomain());
        model.setCreated(entity.getCreated());
        model.setModified(entity.getModified());
        return model;
    }

    @Override
    public CustomPageDomain convertToEntity(CustomPageDomainModel model) {
        if (model == null) {
            return null;
        }
        CustomPageDomain entity = new CustomPageDomain();
        entity.setId(model.getId());
        entity.setTenantId(model.getTenantId());
        entity.setProjectId(model.getProjectId());
        entity.setDomain(model.getDomain());
        entity.setCreated(model.getCreated());
        entity.setModified(model.getModified());
        return entity;
    }
}
