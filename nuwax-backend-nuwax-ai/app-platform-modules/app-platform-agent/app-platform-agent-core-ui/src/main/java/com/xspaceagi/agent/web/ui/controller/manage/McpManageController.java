package com.xspaceagi.agent.web.ui.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.agent.web.ui.controller.manage.dto.BaseManageItem;
import com.xspaceagi.agent.web.ui.controller.manage.dto.ManagePageResponse;
import com.xspaceagi.agent.web.ui.controller.manage.dto.ManageQueryRequest;
import com.xspaceagi.mcp.sdk.IMcpApiService;
import com.xspaceagi.mcp.sdk.dto.McpDto;
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

import static com.xspaceagi.system.spec.enums.ResourceEnum.CONTENT_MCP_DELETE;
import static com.xspaceagi.system.spec.enums.ResourceEnum.CONTENT_MCP_QUERY_LIST;

@Tag(name = "资源管理-MCP管理")
@RestController
@RequestMapping("/api/system/resource/mcp")
public class McpManageController extends BaseManageController {

    @Resource
    private IMcpApiService mcpApiService;

    @Resource
    private IUserRpcService userRpcService;

    @RequireResource(CONTENT_MCP_QUERY_LIST)
    @Operation(summary = "查询MCP列表")
    @PostMapping("/list")
    public ReqResult<ManagePageResponse<BaseManageItem>> list(@RequestBody ManageQueryRequest request) {
        completeCreatorIds(request);
        IPage<McpDto> resultPage = mcpApiService.queryListForManage(
                request.getPageNo(),
                request.getPageSize(),
                request.getName(),
                request.getCreatorIds(),
                request.getSpaceId()
        );

        // 批量查询用户信息
        List<Long> creatorIds = resultPage.getRecords().stream()
                .map(McpDto::getCreatorId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, UserContext> userMap = userRpcService.queryUserListByIds(creatorIds).stream()
                .collect(Collectors.toMap(UserContext::getUserId, user -> user));

        List<BaseManageItem> items = resultPage.getRecords().stream()
                .map(mcp -> {
                    UserContext user = userMap.get(mcp.getCreatorId());
                    return BaseManageItem.builder()
                            .id(mcp.getId())
                            .spaceId(mcp.getSpaceId())
                            .name(mcp.getName())
                            .description(mcp.getDescription())
                            .creatorId(mcp.getCreatorId())
                            .creatorName(user != null ? user.getUserName() : null)
                            .created(mcp.getCreated())
                            .operation("mcp")
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

    @RequireResource(CONTENT_MCP_DELETE)
    @Operation(summary = "删除MCP")
    @PostMapping("/delete/{id}")
    public ReqResult<Void> delete(@PathVariable Long id) {
        mcpApiService.deleteForManage(id);
        return ReqResult.success(null);
    }
}