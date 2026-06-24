package com.xspaceagi.agent.web.ui.controller.manage;

import com.xspaceagi.agent.web.ui.controller.base.BaseController;
import com.xspaceagi.agent.web.ui.controller.manage.dto.BaseManageItem;
import com.xspaceagi.agent.web.ui.controller.manage.dto.ManageQueryRequest;
import com.xspaceagi.system.application.dto.SpaceDto;
import com.xspaceagi.system.application.service.SpaceApplicationService;
import com.xspaceagi.system.sdk.server.IUserRpcService;
import com.xspaceagi.system.sdk.service.dto.UserDetailDto;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseManageController extends BaseController {

    @Resource
    private IUserRpcService iUserRpcService;

    @Resource
    private SpaceApplicationService spaceApplicationService;

    protected void completeCreatorIds(ManageQueryRequest request) {
        if (StringUtils.isNotBlank(request.getCreatorName())) {
            UserDetailDto userDetailDto = iUserRpcService.queryUserDetailByName(request.getCreatorName());
            if (userDetailDto != null) {
                if (request.getCreatorIds() == null) {
                    request.setCreatorIds(new ArrayList<>());
                }
                request.getCreatorIds().add(userDetailDto.getId());
            } else {
                request.setCreatorIds(List.of(-1L));
            }
        }
    }

    protected List<Long> extractExistSpaceIds(List<BaseManageItem> items) {
        List<Long> ids = items.stream().map(BaseManageItem::getSpaceId).toList();
        if (ids.isEmpty()) {
            return List.of();
        }
        List<SpaceDto> spaceDtos = spaceApplicationService.queryByIds(ids);
        return spaceDtos.stream().map(SpaceDto::getId).toList();
    }
}
