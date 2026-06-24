package com.xspaceagi.agent.web.ui.controller;

import com.xspaceagi.agent.core.adapter.application.ComponentApplicationService;
import com.xspaceagi.agent.core.adapter.dto.ComponentDto;
import com.xspaceagi.agent.core.spec.enums.ComponentTypeEnum;
import com.xspaceagi.agent.web.ui.controller.util.SpaceObjectPermissionUtil;
import com.xspaceagi.system.sdk.permission.SpacePermissionService;
import com.xspaceagi.system.spec.annotation.RequireResource;
import com.xspaceagi.system.spec.common.RequestContext;
import com.xspaceagi.system.spec.dto.ReqResult;
import com.xspaceagi.system.application.dto.SpaceUserDto;
import com.xspaceagi.system.application.service.SpaceApplicationService;
import com.xspaceagi.system.application.util.DefaultIconUrlUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.xspaceagi.system.spec.enums.ResourceEnum.COMPONENT_LIB_QUERY_LIST;

@Tag(name = "组件库接口")
@RestController
@RequestMapping("/api/component")
@Slf4j
public class ComponentController {

    @Resource
    private ComponentApplicationService componentApplicationService;

    @Resource
    private SpacePermissionService spacePermissionService;

    @Resource
    private SpaceApplicationService spaceApplicationService;

    @RequireResource(COMPONENT_LIB_QUERY_LIST)
    @Operation(summary = "查询组件列表接口")
    @RequestMapping(path = "/list/{spaceId}", method = RequestMethod.GET)
    public ReqResult<List<ComponentDto>> list(@PathVariable Long spaceId) {
        spacePermissionService.checkSpaceUserPermission(spaceId);
        List<ComponentDto> componentDtos = componentApplicationService.getComponentListBySpaceId(spaceId);
        SpaceUserDto spaceUserDto = spaceApplicationService.querySpaceUser(spaceId, RequestContext.get().getUserId());
        componentDtos.forEach(componentDto -> {
            List<String> collect = SpaceObjectPermissionUtil.generatePermissionList(spaceUserDto, componentDto.getCreatorId()).stream().map(permission -> permission.name()).collect(Collectors.toList());
            componentDto.setPermissions(collect);
            if (componentDto.getType() != ComponentTypeEnum.Model) {
                componentDto.setIcon(DefaultIconUrlUtil.setDefaultIconUrl(componentDto.getIcon(), componentDto.getName(), componentDto.getType().name()));
            }
        });
        return ReqResult.success(componentDtos);
    }

}
