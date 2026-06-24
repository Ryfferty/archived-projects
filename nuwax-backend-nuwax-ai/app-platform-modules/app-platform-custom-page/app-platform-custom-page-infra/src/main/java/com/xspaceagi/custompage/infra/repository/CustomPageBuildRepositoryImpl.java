package com.xspaceagi.custompage.infra.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xspaceagi.agent.core.sdk.IAgentRpcService;
import com.xspaceagi.custompage.domain.model.CustomPageBuildModel;
import com.xspaceagi.custompage.domain.repository.ICustomPageBuildRepository;
import com.xspaceagi.custompage.infra.dao.entity.CustomPageBuild;
import com.xspaceagi.custompage.infra.dao.service.ICustomPageBuildService;
import com.xspaceagi.custompage.infra.translator.ICustomPageBuildTranslator;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.enums.YesOrNoEnum;
import com.xspaceagi.system.spec.enums.YnEnum;
import com.xspaceagi.system.spec.page.SuperPage;

import jakarta.annotation.Resource;

@Repository
public class CustomPageBuildRepositoryImpl implements ICustomPageBuildRepository {

        @Resource
        private ICustomPageBuildService customPageBuildService;
        @Resource
        private ICustomPageBuildTranslator customPageBuildTranslator;
        @Resource
        private IAgentRpcService agentRpcService;

        @Override
        public CustomPageBuildModel getByProjectId(Long projectId) {
                CustomPageBuild entity = customPageBuildService.getOne(Wrappers.<CustomPageBuild>lambdaQuery()
                                .eq(CustomPageBuild::getProjectId, projectId)
                                .eq(CustomPageBuild::getYn, YnEnum.Y.getKey())
                                .last("limit 1"));
                return customPageBuildTranslator.convertToModel(entity);
        }

        @Override
        public void updateKeepAlive(CustomPageBuildModel model, UserContext userContext) {
                LambdaUpdateWrapper<CustomPageBuild> wrapper = Wrappers.<CustomPageBuild>lambdaUpdate()
                                .eq(CustomPageBuild::getProjectId, model.getProjectId())
                                .eq(CustomPageBuild::getYn, YnEnum.Y.getKey())
                                .set(CustomPageBuild::getDevRunning, model.getDevRunning())
                                .set(CustomPageBuild::getLastKeepAliveTime, model.getLastKeepAliveTime());

                if (userContext != null) {
                        wrapper.set(CustomPageBuild::getModifiedId, userContext.getUserId());
                        wrapper.set(CustomPageBuild::getModifiedName, userContext.getUserName());
                }

                // 根据 devRunning 的值来设置 dev_pid, dev_port
                if (Objects.equals(model.getDevRunning(), YesOrNoEnum.N.getKey())) {
                        // 停止状态：清空相关字段
                        wrapper.set(CustomPageBuild::getDevPid, null);
                        wrapper.set(CustomPageBuild::getDevPort, null);
                } else {
                        // 运行状态：更新相关字段
                        wrapper.set(CustomPageBuild::getDevPid, model.getDevPid());
                        wrapper.set(CustomPageBuild::getDevPort, model.getDevPort());
                }

                customPageBuildService.update(null, wrapper);
        }

        @Override
        public void updateBuildStatus(Long projectId, Integer codeVersion, UserContext userContext) {
                customPageBuildService.update(null,
                                Wrappers.<CustomPageBuild>lambdaUpdate()
                                                .eq(CustomPageBuild::getProjectId, projectId)
                                                .eq(CustomPageBuild::getYn, YnEnum.Y.getKey())
                                                .set(CustomPageBuild::getBuildRunning, YesOrNoEnum.Y.getKey())
                                                .set(CustomPageBuild::getBuildVersion, codeVersion)
                                                .set(CustomPageBuild::getBuildTime, new Date())
                                                .set(CustomPageBuild::getModifiedId, userContext.getUserId())
                                                .set(CustomPageBuild::getModifiedName, userContext.getUserName()));
        }

        @Override
        public void updateStopDevStatus(Long projectId, UserContext userContext) {
                LambdaUpdateWrapper<CustomPageBuild> wrapper = Wrappers.<CustomPageBuild>lambdaUpdate()
                                .eq(CustomPageBuild::getProjectId, projectId)
                                .eq(CustomPageBuild::getYn, YnEnum.Y.getKey())
                                .set(CustomPageBuild::getDevRunning, YesOrNoEnum.N.getKey())
                                .set(CustomPageBuild::getDevPid, null)
                                .set(CustomPageBuild::getDevPort, null);
                if (userContext != null) {
                        wrapper.set(CustomPageBuild::getModifiedId, userContext.getUserId());
                        wrapper.set(CustomPageBuild::getModifiedName, userContext.getUserName());
                }
                customPageBuildService.update(null, wrapper);
        }

        @Override
        public Long add(CustomPageBuildModel model, UserContext userContext) {
                CustomPageBuild entity = customPageBuildTranslator.convertToEntity(model);
                customPageBuildService.save(entity);
                return entity.getId();
        }

        @Override
        public SuperPage<CustomPageBuildModel> pageQuery(CustomPageBuildModel model, Long current,
                        Long pageSize) {
                Page<CustomPageBuild> page = new Page<>(current == null ? 1 : current,
                                pageSize == null ? 10 : pageSize);
                var wrapper = Wrappers.<CustomPageBuild>lambdaQuery()
                                .eq(model.getSpaceId() != null, CustomPageBuild::getSpaceId, model.getSpaceId())
                                .eq(model.getBuildRunning() != null, CustomPageBuild::getBuildRunning,
                                                model.getBuildRunning())
                                .eq(model.getCreatorId() != null, CustomPageBuild::getCreatorId, model.getCreatorId())
                                .eq(CustomPageBuild::getYn, YnEnum.Y.getKey())
                                .orderByDesc(CustomPageBuild::getCreated);
                var iPage = customPageBuildService.page(page, wrapper);

                var records = iPage.getRecords().stream()
                                .map(customPageBuildTranslator::convertToModel)
                                .toList();
                return new SuperPage<>(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), records);
        }

        @Override
        public List<CustomPageBuildModel> list(CustomPageBuildModel model) {
                var wrapper = Wrappers.<CustomPageBuild>lambdaQuery()
                                .eq(model.getSpaceId() != null, CustomPageBuild::getSpaceId, model.getSpaceId())
                                .eq(model.getBuildRunning() != null, CustomPageBuild::getBuildRunning,
                                                model.getBuildRunning())
                                .eq(model.getCreatorId() != null, CustomPageBuild::getCreatorId, model.getCreatorId())
                                .eq(CustomPageBuild::getYn, YnEnum.Y.getKey())
                                .orderByDesc(CustomPageBuild::getCreated);
                List<CustomPageBuild> entites = customPageBuildService.list(wrapper);
                if (entites == null || entites.isEmpty()) {
                        return null;
                }
                return entites.stream().map(customPageBuildTranslator::convertToModel).collect(Collectors.toList());
        }

        @Override
        public List<CustomPageBuildModel> listByProjectIds(List<Long> projectIdList) {
                var wrapper = Wrappers.<CustomPageBuild>lambdaQuery()
                                .in(CustomPageBuild::getProjectId, projectIdList)
                                .eq(CustomPageBuild::getYn, YnEnum.Y.getKey())
                                .orderByDesc(CustomPageBuild::getModified);
                List<CustomPageBuild> entites = customPageBuildService.list(wrapper);
                if (entites == null || entites.isEmpty()) {
                        return new ArrayList<>();
                }
                return entites.stream().map(customPageBuildTranslator::convertToModel).collect(Collectors.toList());
        }

        @Override
        public void deleteByProjectId(Long projectId, UserContext userContext) {
                customPageBuildService.remove(
                                Wrappers.<CustomPageBuild>lambdaUpdate().eq(CustomPageBuild::getProjectId, projectId));
                // 逻辑删除
                /*
                 * customPageBuildService.update(null, Wrappers.<CustomPageBuild>lambdaUpdate()
                 * .eq(CustomPageBuild::getProjectId, projectId)
                 * .eq(CustomPageBuild::getYn, YnEnum.Y.getKey())
                 * .set(CustomPageBuild::getYn, YnEnum.N.getKey())
                 * .set(CustomPageBuild::getDevRunning, YesOrNoEnum.N.getKey())
                 * .set(CustomPageBuild::getDevPid, null)
                 * .set(CustomPageBuild::getDevPort, null)
                 * .set(CustomPageBuild::getBuildRunning, YesOrNoEnum.N.getKey())
                 * .set(CustomPageBuild::getModifiedId, userContext.getUserId())
                 * .set(CustomPageBuild::getModifiedName, userContext.getUserName()));
                 */
        }

        @Override
        public List<CustomPageBuildModel> listByDevRunning(Integer devRunning) {
                var list = customPageBuildService.list(Wrappers.<CustomPageBuild>lambdaQuery()
                                .eq(devRunning != null, CustomPageBuild::getDevRunning, devRunning)
                                .eq(CustomPageBuild::getYn, YnEnum.Y.getKey()));
                return list.stream()
                                .map(customPageBuildTranslator::convertToModel)
                                .toList();
        }

        @Override
        public void updateVersionInfo(CustomPageBuildModel updateModel, UserContext userContext) {
                CustomPageBuild entity = customPageBuildTranslator.convertToEntity(updateModel);
                if (userContext != null) {
                        entity.setModifiedId(userContext.getUserId());
                        entity.setModifiedName(userContext.getUserName());
                }
                customPageBuildService.updateById(entity);
        }
}
