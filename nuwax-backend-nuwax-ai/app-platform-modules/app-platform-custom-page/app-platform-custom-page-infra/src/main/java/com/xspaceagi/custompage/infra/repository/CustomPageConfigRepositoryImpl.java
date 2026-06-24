package com.xspaceagi.custompage.infra.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.enums.YesOrNoEnum;
import com.xspaceagi.system.spec.enums.YnEnum;
import com.xspaceagi.system.spec.page.SuperPage;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xspaceagi.custompage.domain.model.CustomPageConfigModel;
import com.xspaceagi.custompage.domain.repository.ICustomPageConfigRepository;
import com.xspaceagi.custompage.infra.dao.entity.CustomPageConfig;
import com.xspaceagi.custompage.infra.dao.service.ICustomPageConfigService;
import com.xspaceagi.custompage.infra.translator.ICustomPageConfigTranslator;
import com.xspaceagi.custompage.sdk.dto.PublishTypeEnum;

import jakarta.annotation.Resource;

@Repository
public class CustomPageConfigRepositoryImpl implements ICustomPageConfigRepository {

        @Resource
        private ICustomPageConfigService customPageConfigService;
        @Resource
        private ICustomPageConfigTranslator customPageConfigTranslator;

        @Override
        public List<CustomPageConfigModel> list(CustomPageConfigModel model) {
                var wrapper = Wrappers.<CustomPageConfig>lambdaQuery()
                                .eq(model.getSpaceId() != null, CustomPageConfig::getSpaceId, model.getSpaceId())
                                .eq(model.getCreatorId() != null, CustomPageConfig::getCreatorId, model.getCreatorId())
                                .eq(model.getBuildRunning() != null, CustomPageConfig::getBuildRunning,
                                                model.getBuildRunning())
                                .eq(model.getPublishType() != null, CustomPageConfig::getPublishType,
                                                model.getPublishType())
                                .eq(CustomPageConfig::getYn, YnEnum.Y.getKey())
                                .orderByDesc(CustomPageConfig::getModified);
                List<CustomPageConfig> entities = customPageConfigService.list(wrapper);
                if (entities == null || entities.isEmpty()) {
                        return null;
                }
                return entities.stream().map(customPageConfigTranslator::convertToModel).collect(Collectors.toList());
        }

        @Override
        public SuperPage<CustomPageConfigModel> pageQuery(CustomPageConfigModel model, Long current, Long pageSize) {

                Page<CustomPageConfig> page = new Page<>(current == null ? 1 : current,
                                pageSize == null ? 10 : pageSize);
                var wrapper = Wrappers.<CustomPageConfig>lambdaQuery()
                                .eq(model.getSpaceId() != null, CustomPageConfig::getSpaceId, model.getSpaceId())
                                .eq(model.getCreatorId() != null,
                                                CustomPageConfig::getCreatorId, model.getCreatorId())
                                .eq(model.getBuildRunning() != null,
                                                CustomPageConfig::getBuildRunning, model.getBuildRunning())
                                .eq(CustomPageConfig::getYn, YnEnum.Y.getKey())
                                .orderByDesc(CustomPageConfig::getModified);
                var iPage = customPageConfigService.page(page, wrapper);

                var records = iPage.getRecords().stream()
                                .map(customPageConfigTranslator::convertToModel)
                                .toList();
                return new SuperPage<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), records);
        }

        @Override
        public CustomPageConfigModel getById(Long id) {
                CustomPageConfig entity = customPageConfigService.getOne(Wrappers.<CustomPageConfig>lambdaQuery()
                                .eq(CustomPageConfig::getId, id)
                                .eq(CustomPageConfig::getYn, YnEnum.Y.getKey())
                                .last("limit 1"));
                return customPageConfigTranslator.convertToModel(entity);
        }

        @Override
        public CustomPageConfigModel getByAgentId(Long agentId) {
            CustomPageConfig entity = customPageConfigService.getOne(Wrappers.<CustomPageConfig>lambdaQuery()
                    .eq(CustomPageConfig::getDevAgentId, agentId)
                    .eq(CustomPageConfig::getYn, YnEnum.Y.getKey())
                    .last("limit 1"));
            return customPageConfigTranslator.convertToModel(entity);
        }

        @Override
        public CustomPageConfigModel getByBasePath(String basePath) {
                CustomPageConfig entity = customPageConfigService.getOne(Wrappers.<CustomPageConfig>lambdaQuery()
                                .eq(CustomPageConfig::getBasePath, basePath)
                                .eq(CustomPageConfig::getYn, YnEnum.Y.getKey())
                                .last("limit 1"));
                return customPageConfigTranslator.convertToModel(entity);
        }

        @Override
        public Long add(CustomPageConfigModel model, UserContext userContext) {
                CustomPageConfig entity = customPageConfigTranslator.convertToEntity(model);
                customPageConfigService.save(entity);
                return entity.getId();
        }

        @Override
        public void updateById(CustomPageConfigModel model, UserContext userContext) {
                CustomPageConfig updateEntity = customPageConfigTranslator.convertToEntity(model);
                customPageConfigService.update(updateEntity, Wrappers.<CustomPageConfig>lambdaUpdate()
                                .eq(CustomPageConfig::getId, model.getId())
                                .eq(CustomPageConfig::getYn, YnEnum.Y.getKey()));
        }

        @Override
        public void updateBuildStatus(Long projectId, PublishTypeEnum publishTypeEnum, UserContext userContext) {
                customPageConfigService.update(null, Wrappers.<CustomPageConfig>lambdaUpdate()
                                .eq(CustomPageConfig::getId, projectId)
                                .eq(CustomPageConfig::getYn, YnEnum.Y.getKey())
                                .set(CustomPageConfig::getBuildRunning, YesOrNoEnum.Y.getKey())
                                .set(CustomPageConfig::getPublishType, publishTypeEnum)
                                .set(CustomPageConfig::getModifiedId, userContext.getUserId())
                                .set(CustomPageConfig::getModifiedName, userContext.getUserName()));
        }

        @Override
        public void deleteById(Long projectId, UserContext userContext) {
                customPageConfigService.removeById(projectId);

                // 逻辑删除
                /*
                 * customPageConfigService.update(null,
                 * Wrappers.<CustomPageConfig>lambdaUpdate()
                 * .eq(CustomPageConfig::getId, projectId)
                 * .eq(CustomPageConfig::getYn, YnEnum.Y.getKey())
                 * .set(CustomPageConfig::getYn, YnEnum.N.getKey())
                 * .set(CustomPageConfig::getBuildRunning, YesOrNoEnum.N.getKey())
                 * .set(CustomPageConfig::getModifiedId, userContext.getUserId())
                 * .set(CustomPageConfig::getModifiedName, userContext.getUserName()));
                 */
        }

        @Override
        public List<CustomPageConfigModel> listByDevAgentIds(List<Long> devAgentIds) {
                if (devAgentIds == null || devAgentIds.isEmpty()) {
                        return new ArrayList<>();
                }
                var wrapper = Wrappers.<CustomPageConfig>lambdaQuery()
                                .in(CustomPageConfig::getDevAgentId, devAgentIds)
                                .eq(CustomPageConfig::getYn, YnEnum.Y.getKey());
                List<CustomPageConfig> entities = customPageConfigService.list(wrapper);
                if (entities == null || entities.isEmpty()) {
                        return new ArrayList<>();
                }
                return entities.stream().map(customPageConfigTranslator::convertToModel).collect(Collectors.toList());
        }

    @Override
    public List<CustomPageConfigModel> listByIds(List<Long> ids) {
        var wrapper = Wrappers.<CustomPageConfig>lambdaQuery()
                .in(CustomPageConfig::getId, ids)
                .eq(CustomPageConfig::getYn, YnEnum.Y.getKey());
        List<CustomPageConfig> entities = customPageConfigService.list(wrapper);
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        return entities.stream().map(customPageConfigTranslator::convertToModel).collect(Collectors.toList());
    }

    @Override
        public Long countTotalPages() {
                return customPageConfigService.count();
        }
}
