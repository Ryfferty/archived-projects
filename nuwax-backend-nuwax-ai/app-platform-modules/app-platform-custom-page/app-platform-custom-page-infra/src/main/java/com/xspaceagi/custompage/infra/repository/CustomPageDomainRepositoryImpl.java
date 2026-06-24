package com.xspaceagi.custompage.infra.repository;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xspaceagi.custompage.domain.model.CustomPageDomainModel;
import com.xspaceagi.custompage.domain.repository.ICustomPageDomainRepository;
import com.xspaceagi.custompage.infra.dao.entity.CustomPageDomain;
import com.xspaceagi.custompage.infra.dao.service.ICustomPageDomainService;
import com.xspaceagi.custompage.infra.translator.ICustomPageDomainTranslator;

import jakarta.annotation.Resource;

@Repository
public class CustomPageDomainRepositoryImpl implements ICustomPageDomainRepository {

    @Resource
    private ICustomPageDomainService customPageDomainService;

    @Resource
    private ICustomPageDomainTranslator customPageDomainTranslator;

    @Override
    public List<CustomPageDomainModel> listByProjectId(Long projectId) {
        var wrapper = Wrappers.<CustomPageDomain>lambdaQuery()
                .eq(CustomPageDomain::getProjectId, projectId)
                .orderByDesc(CustomPageDomain::getCreated);
        List<CustomPageDomain> entities = customPageDomainService.list(wrapper);
        if (entities == null || entities.isEmpty()) {
            return List.of();
        }
        return entities.stream()
                .map(customPageDomainTranslator::convertToModel)
                .collect(Collectors.toList());
    }

    @Override
    public CustomPageDomainModel getById(Long id) {
        CustomPageDomain entity = customPageDomainService.getById(id);
        return customPageDomainTranslator.convertToModel(entity);
    }

    @Override
    public CustomPageDomainModel getByDomain(String domain) {
        CustomPageDomain entity = customPageDomainService.getOne(
                Wrappers.<CustomPageDomain>lambdaQuery()
                        .eq(CustomPageDomain::getDomain, domain)
                        .last("limit 1")
        );
        return customPageDomainTranslator.convertToModel(entity);
    }

    @Override
    public CustomPageDomainModel add(CustomPageDomainModel model) {
        CustomPageDomain entity = customPageDomainTranslator.convertToEntity(model);
        customPageDomainService.save(entity);
        return customPageDomainTranslator.convertToModel(entity);
    }

    @Override
    public void updateById(CustomPageDomainModel model) {
        CustomPageDomain entity = customPageDomainTranslator.convertToEntity(model);
        customPageDomainService.updateById(entity);
    }

    @Override
    public void removeById(Long id) {
        customPageDomainService.removeById(id);
    }

    @Override
    public List<String> listAllDomains() {
        LambdaQueryWrapper<CustomPageDomain> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(CustomPageDomain::getDomain, CustomPageDomain::getProjectId);

        return customPageDomainService.list()
                .stream()
                .map(CustomPageDomain::getDomain)
                .collect(Collectors.toList());
    }
}
