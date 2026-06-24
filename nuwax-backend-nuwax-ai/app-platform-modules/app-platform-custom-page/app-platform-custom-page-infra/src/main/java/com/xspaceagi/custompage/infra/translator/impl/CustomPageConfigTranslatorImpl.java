package com.xspaceagi.custompage.infra.translator.impl;

import org.springframework.stereotype.Component;

import com.xspaceagi.custompage.domain.model.CustomPageConfigModel;
import com.xspaceagi.custompage.infra.dao.entity.CustomPageConfig;
import com.xspaceagi.custompage.infra.translator.ICustomPageConfigTranslator;

@Component
public class CustomPageConfigTranslatorImpl implements ICustomPageConfigTranslator {

    @Override
    public CustomPageConfigModel convertToModel(CustomPageConfig entity) {
        if (entity == null) {
            return null;
        }
        CustomPageConfigModel model = new CustomPageConfigModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setIcon(entity.getIcon());
        model.setCoverImg(entity.getCoverImg());
        model.setCoverImgSourceType(entity.getCoverImgSourceType());
        model.setBasePath(entity.getBasePath());
        model.setBuildRunning(entity.getBuildRunning());
        model.setPublishType(entity.getPublishType());
        model.setNeedLogin(entity.getNeedLogin());
        model.setDevAgentId(entity.getDevAgentId());
        model.setProjectType(entity.getProjectType());
        model.setProxyConfigs(entity.getProxyConfigs());
        model.setPageArgConfigs(entity.getPageArgConfigs());
        model.setDataSources(entity.getDataSources());
        model.setSandboxId(entity.getSandboxId());
        model.setExt(entity.getExt());
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
    public CustomPageConfig convertToEntity(CustomPageConfigModel model) {
        if (model == null) {
            return null;
        }
        CustomPageConfig entity = new CustomPageConfig();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDescription(model.getDescription());
        entity.setIcon(model.getIcon());
        entity.setCoverImg(model.getCoverImg());
        entity.setCoverImgSourceType(model.getCoverImgSourceType());
        entity.setBasePath(model.getBasePath());
        entity.setBuildRunning(model.getBuildRunning());
        entity.setPublishType(model.getPublishType());
        entity.setNeedLogin(model.getNeedLogin());
        entity.setDevAgentId(model.getDevAgentId());
        entity.setProjectType(model.getProjectType());
        entity.setProxyConfigs(model.getProxyConfigs());
        entity.setPageArgConfigs(model.getPageArgConfigs());
        entity.setDataSources(model.getDataSources());
        entity.setSandboxId(model.getSandboxId());
        entity.setExt(model.getExt());
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
