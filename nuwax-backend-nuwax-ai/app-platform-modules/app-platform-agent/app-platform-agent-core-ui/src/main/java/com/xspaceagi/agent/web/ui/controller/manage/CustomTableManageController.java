package com.xspaceagi.agent.web.ui.controller.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xspaceagi.agent.web.ui.controller.manage.dto.BaseManageItem;
import com.xspaceagi.agent.web.ui.controller.manage.dto.ManagePageResponse;
import com.xspaceagi.agent.web.ui.controller.manage.dto.ManageQueryRequest;
import com.xspaceagi.compose.sdk.service.IComposeDbTableRpcService;
import com.xspaceagi.compose.sdk.vo.define.TableDefineVo;
import com.xspaceagi.system.sdk.server.IUserRpcService;
import com.xspaceagi.system.spec.annotation.RequireResource;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.spec.dto.ReqResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.xspaceagi.system.spec.enums.ResourceEnum.CONTENT_DATATABLE_DELETE;
import static com.xspaceagi.system.spec.enums.ResourceEnum.CONTENT_DATATABLE_QUERY_LIST;

@Tag(name = "资源管理-数据表管理")
@RestController
@RequestMapping("/api/system/resource/table")
public class CustomTableManageController extends BaseManageController {

    @Resource
    private IComposeDbTableRpcService composeDbTableRpcService;

    @Resource
    private IUserRpcService userRpcService;

    @RequireResource(CONTENT_DATATABLE_QUERY_LIST)
    @Operation(summary = "查询数据表列表")
    @PostMapping("/list")
    public ReqResult<ManagePageResponse<BaseManageItem>> list(@RequestBody ManageQueryRequest request) {
        completeCreatorIds(request);
        IPage<TableDefineVo> resultPage = composeDbTableRpcService.queryListForManage(
                request.getPageNo(),
                request.getPageSize(),
                request.getName(),
                request.getCreatorIds(),
                request.getSpaceId()
        );

        // 批量查询用户信息
        List<Long> creatorIds = resultPage.getRecords().stream()
                .map(TableDefineVo::getCreatorId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, UserContext> userMap = userRpcService.queryUserListByIds(creatorIds).stream()
                .collect(Collectors.toMap(UserContext::getUserId, user -> user));

        List<BaseManageItem> items = resultPage.getRecords().stream()
                .map(table -> {
                    Date created = null;
                    if (table.getCreated() != null) {
                        created = Date.from(table.getCreated().atZone(java.time.ZoneId.systemDefault()).toInstant());
                    }
                    UserContext user = userMap.get(table.getCreatorId());
                    return BaseManageItem.builder()
                            .id(table.getId())
                            .spaceId(table.getSpaceId())
                            .name(table.getTableName())
                            .description(table.getTableDescription())
                            .creatorId(table.getCreatorId())
                            .creatorName(user != null ? user.getUserName() : null)
                            .created(created)
                            .operation("table")
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

    @RequireResource(CONTENT_DATATABLE_DELETE)
    @Operation(summary = "删除数据表")
    @PostMapping("/delete/{id}")
    public ReqResult<Void> delete(@PathVariable Long id) {
        composeDbTableRpcService.deleteForManage(id);
        return ReqResult.success(null);
    }
}