package com.xspaceagi.agent.web.ui.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xspaceagi.agent.core.adapter.application.SkillApplicationService;
import com.xspaceagi.agent.core.adapter.repository.entity.SkillConfig;
import com.xspaceagi.agent.core.infra.dao.mapper.SkillConfigMapper;
import com.xspaceagi.agent.web.ui.controller.manage.dto.BaseManageItem;
import com.xspaceagi.agent.web.ui.controller.manage.dto.ManagePageResponse;
import com.xspaceagi.agent.web.ui.controller.manage.dto.ManageQueryRequest;
import com.xspaceagi.system.sdk.server.IUserRpcService;
import com.xspaceagi.system.spec.annotation.RequireResource;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.dto.ReqResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.xspaceagi.system.spec.enums.ResourceEnum.CONTENT_SKILL_DELETE;
import static com.xspaceagi.system.spec.enums.ResourceEnum.CONTENT_SKILL_QUERY_LIST;

@Tag(name = "资源管理-技能管理")
@RestController
@RequestMapping("/api/system/resource/skill")
public class SkillManageController extends BaseManageController {

    @Resource
    private SkillApplicationService skillApplicationService;

    @Resource
    private SkillConfigMapper skillConfigMapper;

    @Resource
    private IUserRpcService userRpcService;

    @RequireResource(CONTENT_SKILL_QUERY_LIST)
    @Operation(summary = "查询技能列表")
    @PostMapping("/list")
    public ReqResult<ManagePageResponse<BaseManageItem>> list(@RequestBody ManageQueryRequest request) {
        Page<SkillConfig> page = new Page<>(request.getPageNo(), request.getPageSize());
        completeCreatorIds(request);
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SkillConfig> queryWrapper =
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<SkillConfig>()
                        .select(SkillConfig::getId, SkillConfig::getName, SkillConfig::getDescription, SkillConfig::getSpaceId, SkillConfig::getIcon, SkillConfig::getPublishStatus, SkillConfig::getCreatorId, SkillConfig::getCreatorName, SkillConfig::getCreated, SkillConfig::getModified, SkillConfig::getModifiedId, SkillConfig::getModifiedName, SkillConfig::getYn)
                        .like(request.getName() != null, SkillConfig::getName, request.getName())
                        .in(request.getCreatorIds() != null && !request.getCreatorIds().isEmpty(),
                                SkillConfig::getCreatorId, request.getCreatorIds())
                        .eq(request.getSpaceId() != null, SkillConfig::getSpaceId, request.getSpaceId())
                        .orderByDesc(SkillConfig::getCreated);

        IPage<SkillConfig> resultPage = skillConfigMapper.selectPage(page, queryWrapper);

        // 批量查询用户信息
        List<Long> creatorIds = resultPage.getRecords().stream()
                .map(SkillConfig::getCreatorId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, UserContext> userMap = userRpcService.queryUserListByIds(creatorIds).stream()
                .collect(Collectors.toMap(UserContext::getUserId, user -> user));

        List<BaseManageItem> items = resultPage.getRecords().stream()
                .map(skill -> {
                    UserContext user = userMap.get(skill.getCreatorId());
                    return BaseManageItem.builder()
                            .id(skill.getId())
                            .name(skill.getName())
                            .description(skill.getDescription())
                            .creatorId(skill.getCreatorId())
                            .creatorName(user != null ? user.getUserName() : null)
                            .created(skill.getCreated())
                            .operation("skill")
                            .spaceId(skill.getSpaceId())
                            .build();
                })
                .collect(Collectors.toList());

        List<Long> longs = extractExistSpaceIds(items);
        items.removeIf(item -> !longs.contains(item.getSpaceId()));

        ManagePageResponse<BaseManageItem> response = ManagePageResponse.<BaseManageItem>builder()
                .total(resultPage.getTotal())
                .pageNo(request.getPageNo())
                .pageSize(request.getPageSize())
                .records(items)
                .build();

        return ReqResult.success(response);
    }

    @RequireResource(CONTENT_SKILL_DELETE)
    @Operation(summary = "删除技能")
    @PostMapping("/delete/{id}")
    public ReqResult<Void> delete(@PathVariable Long id) {
        skillApplicationService.delete(id);
        return ReqResult.success(null);
    }
}