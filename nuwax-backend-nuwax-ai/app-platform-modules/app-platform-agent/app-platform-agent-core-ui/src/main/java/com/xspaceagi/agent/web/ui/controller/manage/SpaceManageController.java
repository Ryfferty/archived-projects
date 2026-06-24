package com.xspaceagi.agent.web.ui.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xspaceagi.agent.web.ui.controller.manage.dto.BaseManageItem;
import com.xspaceagi.agent.web.ui.controller.manage.dto.ManagePageResponse;
import com.xspaceagi.agent.web.ui.controller.manage.dto.ManageQueryRequest;
import com.xspaceagi.system.infra.dao.entity.Space;
import com.xspaceagi.system.infra.dao.mapper.SpaceMapper;
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

import static com.xspaceagi.system.spec.enums.ResourceEnum.CONTENT_SPACE_DELETE;
import static com.xspaceagi.system.spec.enums.ResourceEnum.CONTENT_SPACE_QUERY_LIST;

@Tag(name = "资源管理-工作空间管理")
@RestController
@RequestMapping("/api/system/resource/space")
public class SpaceManageController extends BaseManageController {

    @Resource
    private SpaceMapper spaceMapper;

    @Resource
    private IUserRpcService userRpcService;

    @RequireResource (CONTENT_SPACE_QUERY_LIST)
    @Operation(summary = "查询工作空间列表")
    @PostMapping("/list")
    public ReqResult<ManagePageResponse<BaseManageItem>> list(@RequestBody ManageQueryRequest request) {
        Page<Space> page = new Page<>(request.getPageNo(), request.getPageSize());
        completeCreatorIds(request);
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Space> queryWrapper =
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Space>()
                        .like(request.getName() != null, Space::getName, request.getName())
                        .eq(Space::getType, Space.Type.Team)
                        .in(request.getCreatorIds() != null && !request.getCreatorIds().isEmpty(),
                                Space::getCreatorId, request.getCreatorIds())
                        .orderByDesc(Space::getCreated);

        IPage<Space> resultPage = spaceMapper.selectPage(page, queryWrapper);

        // 批量查询用户信息
        List<Long> creatorIds = resultPage.getRecords().stream()
                .map(Space::getCreatorId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, UserContext> userMap = userRpcService.queryUserListByIds(creatorIds).stream()
                .collect(Collectors.toMap(UserContext::getUserId, user -> user));

        List<BaseManageItem> items = resultPage.getRecords().stream()
                .map(space -> {
                    UserContext user = userMap.get(space.getCreatorId());
                    return BaseManageItem.builder()
                            .id(space.getId())
                            .spaceId(space.getId())
                            .name(space.getName())
                            .description(space.getDescription())
                            .creatorId(space.getCreatorId())
                            .creatorName(user != null ? user.getUserName() : null)
                            .created(space.getCreated())
                            .operation("space")
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

    @RequireResource (CONTENT_SPACE_DELETE)
    @Operation(summary = "删除工作空间")
    @PostMapping("/delete/{id}")
    public ReqResult<Void> delete(@PathVariable Long id) {
        spaceMapper.deleteById(id);
        return ReqResult.success(null);
    }
}