package com.xspaceagi.agent.web.ui.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xspaceagi.agent.core.adapter.application.WorkflowApplicationService;
import com.xspaceagi.agent.core.adapter.repository.entity.WorkflowConfig;
import com.xspaceagi.agent.core.infra.dao.mapper.WorkflowConfigMapper;
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

import static com.xspaceagi.system.spec.enums.ResourceEnum.CONTENT_WORKFLOW_DELETE;
import static com.xspaceagi.system.spec.enums.ResourceEnum.CONTENT_WORKFLOW_QUERY_LIST;

@Tag(name = "资源管理-工作流管理")
@RestController
@RequestMapping("/api/system/resource/workflow")
public class WorkflowManageController extends BaseManageController {

    @Resource
    private WorkflowApplicationService workflowApplicationService;

    @Resource
    private WorkflowConfigMapper workflowConfigMapper;

    @Resource
    private IUserRpcService userRpcService;

    @RequireResource(CONTENT_WORKFLOW_QUERY_LIST)
    @Operation(summary = "查询工作流列表")
    @PostMapping("/list")
    public ReqResult<ManagePageResponse<BaseManageItem>> list(@RequestBody ManageQueryRequest request) {
        Page<WorkflowConfig> page = new Page<>(request.getPageNo(), request.getPageSize());
        completeCreatorIds(request);
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<WorkflowConfig> queryWrapper =
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<WorkflowConfig>()
                        .like(request.getName() != null, WorkflowConfig::getName, request.getName())
                        .in(request.getCreatorIds() != null && !request.getCreatorIds().isEmpty(),
                                WorkflowConfig::getCreatorId, request.getCreatorIds())
                        .eq(request.getSpaceId() != null, WorkflowConfig::getSpaceId, request.getSpaceId())
                        .orderByDesc(WorkflowConfig::getCreated);

        IPage<WorkflowConfig> resultPage = workflowConfigMapper.selectPage(page, queryWrapper);

        // 批量查询用户信息
        List<Long> creatorIds = resultPage.getRecords().stream()
                .map(WorkflowConfig::getCreatorId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, UserContext> userMap = userRpcService.queryUserListByIds(creatorIds).stream()
                .collect(Collectors.toMap(UserContext::getUserId, user -> user));

        List<BaseManageItem> items = resultPage.getRecords().stream()
                .map(workflow -> {
                    UserContext user = userMap.get(workflow.getCreatorId());
                    return BaseManageItem.builder()
                            .id(workflow.getId())
                            .spaceId(workflow.getSpaceId())
                            .name(workflow.getName())
                            .description(workflow.getDescription())
                            .creatorId(workflow.getCreatorId())
                            .creatorName(user != null ? user.getUserName() : null)
                            .created(workflow.getCreated())
                            .operation("workflow")
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

    @RequireResource(CONTENT_WORKFLOW_DELETE)
    @Operation(summary = "删除工作流")
    @PostMapping("/delete/{id}")
    public ReqResult<Void> delete(@PathVariable Long id) {
        workflowApplicationService.delete(id);
        return ReqResult.success(null);
    }
}