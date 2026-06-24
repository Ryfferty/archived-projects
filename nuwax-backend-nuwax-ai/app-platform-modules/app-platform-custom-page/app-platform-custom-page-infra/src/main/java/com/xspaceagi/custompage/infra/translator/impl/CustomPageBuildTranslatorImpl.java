package com.xspaceagi.custompage.infra.translator.impl;

import org.springframework.stereotype.Component;

import com.xspaceagi.custompage.domain.model.CustomPageBuildModel;
import com.xspaceagi.custompage.infra.dao.entity.CustomPageBuild;
import com.xspaceagi.custompage.infra.translator.ICustomPageBuildTranslator;

@Component
public class CustomPageBuildTranslatorImpl implements ICustomPageBuildTranslator {
    @Override
    public CustomPageBuildModel convertToModel(CustomPageBuild entity) {
        if (entity == null) {
            return null;
        }
        CustomPageBuildModel model = new CustomPageBuildModel();
        model.setId(entity.getId());
        model.setProjectId(entity.getProjectId());
        model.setDevRunning(entity.getDevRunning());
        model.setDevPid(entity.getDevPid());
        model.setDevPort(entity.getDevPort());
        model.setLastKeepAliveTime(entity.getLastKeepAliveTime());
        model.setBuildRunning(entity.getBuildRunning());
        model.setBuildTime(entity.getBuildTime());
        model.setBuildVersion(entity.getBuildVersion());
        model.setCodeVersion(entity.getCodeVersion());
        model.setVersionInfo(entity.getVersionInfo());
        model.setLastChatModelId(entity.getLastChatModelId());
        model.setLastMultiModelId(entity.getLastMultiModelId());
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
    public CustomPageBuild convertToEntity(CustomPageBuildModel model) {
        if (model == null) {
            return null;
        }
        return CustomPageBuild.builder()
                .id(model.getId())
                .projectId(model.getProjectId())
                .devRunning(model.getDevRunning())
                .devPid(model.getDevPid())
                .devPort(model.getDevPort())
                .lastKeepAliveTime(model.getLastKeepAliveTime())
                .buildRunning(model.getBuildRunning())
                .buildTime(model.getBuildTime())
                .buildVersion(model.getBuildVersion())
                .codeVersion(model.getCodeVersion())
                .versionInfo(model.getVersionInfo())
                .lastChatModelId(model.getLastChatModelId())
                .lastMultiModelId(model.getLastMultiModelId())
                .tenantId(model.getTenantId())
                .spaceId(model.getSpaceId())
                .created(model.getCreated())
                .creatorId(model.getCreatorId())
                .creatorName(model.getCreatorName())
                .modified(model.getModified())
                .modifiedId(model.getModifiedId())
                .modifiedName(model.getModifiedName())
                .yn(model.getYn())
                .build();
    }
}
