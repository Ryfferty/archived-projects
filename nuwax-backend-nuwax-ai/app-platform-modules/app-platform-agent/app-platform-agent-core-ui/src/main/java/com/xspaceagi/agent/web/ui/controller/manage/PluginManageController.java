package com.xspaceagi.agent.web.ui.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xspaceagi.agent.core.adapter.application.PluginApplicationService;
import com.xspaceagi.agent.core.adapter.repository.entity.PluginConfig;
import com.xspaceagi.agent.core.infra.dao.mapper.PluginConfigMapper;
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

import static com.xspaceagi.system.spec.enums.ResourceEnum.CONTENT_PLUGIN_DELETE;
import static com.xspaceagi.system.spec.enums.ResourceEnum.CONTENT_PLUGIN_QUERY_LIST;

@Tag(name = "资源管理-插件管理")
@RestController
@RequestMapping("/api/system/resource/plugin")
public class PluginManageController extends BaseManageController {

    @Resource
    private PluginApplicationService pluginApplicationService;

    @Resource
    private PluginConfigMapper pluginConfigMapper;

    @Resource
    private IUserRpcService userRpcService;

    @RequireResource(CONTENT_PLUGIN_QUERY_LIST)
    @Operation(summary = "查询插件列表")
    @PostMapping("/list")
    public ReqResult<ManagePageResponse<BaseManageItem>> list(@RequestBody ManageQueryRequest request) {
        Page<PluginConfig> page = new Page<>(request.getPageNo(), request.getPageSize());
        completeCreatorIds(request);
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<PluginConfig> queryWrapper =
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<PluginConfig>()
                        .like(request.getName() != null, PluginConfig::getName, request.getName())
                        .in(request.getCreatorIds() != null && !request.getCreatorIds().isEmpty(),
                                PluginConfig::getCreatorId, request.getCreatorIds())
                        .eq(request.getSpaceId() != null, PluginConfig::getSpaceId, request.getSpaceId())
                        .orderByDesc(PluginConfig::getCreated);

        IPage<PluginConfig> resultPage = pluginConfigMapper.selectPage(page, queryWrapper);

        // 批量查询用户信息
        List<Long> creatorIds = resultPage.getRecords().stream()
                .map(PluginConfig::getCreatorId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, UserContext> userMap = userRpcService.queryUserListByIds(creatorIds).stream()
                .collect(Collectors.toMap(UserContext::getUserId, user -> user));

        List<BaseManageItem> items = resultPage.getRecords().stream()
                .map(plugin -> {
                    UserContext user = userMap.get(plugin.getCreatorId());
                    return BaseManageItem.builder()
                            .id(plugin.getId())
                            .spaceId(plugin.getSpaceId())
                            .name(plugin.getName())
                            .description(plugin.getDescription())
                            .creatorId(plugin.getCreatorId())
                            .creatorName(user != null ? user.getUserName() : null)
                            .created(plugin.getCreated())
                            .operation("plugin")
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

    @RequireResource(CONTENT_PLUGIN_DELETE)
    @Operation(summary = "删除插件")
    @PostMapping("/delete/{id}")
    public ReqResult<Void> delete(@PathVariable Long id) {
        pluginApplicationService.delete(id);
        return ReqResult.success(null);
    }
}