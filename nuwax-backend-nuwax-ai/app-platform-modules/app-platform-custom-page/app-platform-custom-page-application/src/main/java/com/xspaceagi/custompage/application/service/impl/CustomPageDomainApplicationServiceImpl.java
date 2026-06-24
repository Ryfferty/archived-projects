package com.xspaceagi.custompage.application.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.xspaceagi.custompage.application.service.ICustomPageDomainApplicationService;
import com.xspaceagi.custompage.domain.model.CustomPageDomainModel;
import com.xspaceagi.custompage.domain.service.ICustomPageDomainDomainService;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.dto.ReqResult;

import jakarta.annotation.Resource;

@Service
public class CustomPageDomainApplicationServiceImpl implements ICustomPageDomainApplicationService {

    @Resource
    private ICustomPageDomainDomainService customPageDomainDomainService;

    @Override
    public List<CustomPageDomainModel> listByProjectId(Long projectId) {
        return customPageDomainDomainService.listByProjectId(projectId);
    }

    @Override
    public CustomPageDomainModel getById(Long id) {
        return customPageDomainDomainService.getById(id);
    }

    @Override
    public ReqResult<CustomPageDomainModel> create(CustomPageDomainModel model, UserContext userContext) {
        return customPageDomainDomainService.create(model, userContext);
    }

    @Override
    public ReqResult<CustomPageDomainModel> update(CustomPageDomainModel model, UserContext userContext) {
        return customPageDomainDomainService.update(model, userContext);
    }

    @Override
    public ReqResult<Void> delete(Long id, UserContext userContext) {
        return customPageDomainDomainService.delete(id, userContext);
    }

    @Override
    public List<String> listAllDomains() {
        return customPageDomainDomainService.listAllDomains();
    }
}
