package com.xspaceagi.agent.web.ui.controller.util;

import com.xspaceagi.system.spec.common.RequestContext;
import com.xspaceagi.system.spec.enums.YesOrNoEnum;
import com.xspaceagi.system.application.dto.SpaceUserDto;
import com.xspaceagi.system.application.dto.TenantConfigDto;
import com.xspaceagi.system.application.dto.UserDto;
import com.xspaceagi.system.infra.dao.entity.SpaceUser;
import com.xspaceagi.system.infra.dao.entity.User;

import java.util.ArrayList;
import java.util.List;

public class SpaceObjectPermissionUtil {

    public static List<SpaceObjectPermissionEnum> generatePermissionList(SpaceUserDto spaceUserDto, Long creatorId) {
        List<SpaceObjectPermissionEnum> permissionList = new ArrayList<>();
        permissionList.add(SpaceObjectPermissionEnum.Copy);
        UserDto user = (UserDto) RequestContext.get().getUser();
        if (user != null && user.getRole() == User.Role.Admin) {
            permissionList.add(SpaceObjectPermissionEnum.Transfer);
            permissionList.add(SpaceObjectPermissionEnum.Delete);
            permissionList.add(SpaceObjectPermissionEnum.Publish);
            permissionList.add(SpaceObjectPermissionEnum.TempChat);
            permissionList.add(SpaceObjectPermissionEnum.AgentApi);
            permissionList.add(SpaceObjectPermissionEnum.Export);
            return permissionList;
        }
        if (spaceUserDto == null) {
            permissionList.clear();
            return permissionList;
        }
        TenantConfigDto tenantConfigDto = (TenantConfigDto) RequestContext.get().getTenantConfig();
        boolean isOpenAgentApi = tenantConfigDto.getAllowAgentApi() == null || tenantConfigDto.getAllowAgentApi().equals(YesOrNoEnum.Y.getKey());
        boolean isOpenAgentTempChat = tenantConfigDto.getAllowAgentTempChat() == null || tenantConfigDto.getAllowAgentTempChat().equals(YesOrNoEnum.Y.getKey());
        if (spaceUserDto.getUserId().equals(creatorId)) {
            permissionList.add(SpaceObjectPermissionEnum.Delete);
            permissionList.add(SpaceObjectPermissionEnum.Publish);
            permissionList.add(SpaceObjectPermissionEnum.Export);
            if (spaceUserDto.getRole() == SpaceUser.Role.Admin || spaceUserDto.getRole() == SpaceUser.Role.Owner) {
                permissionList.add(SpaceObjectPermissionEnum.Transfer);
            }
        } else {
            if (spaceUserDto.getRole() == SpaceUser.Role.Admin || spaceUserDto.getRole() == SpaceUser.Role.Owner) {
                permissionList.add(SpaceObjectPermissionEnum.Transfer);
                permissionList.add(SpaceObjectPermissionEnum.Delete);
                permissionList.add(SpaceObjectPermissionEnum.Publish);
                permissionList.add(SpaceObjectPermissionEnum.Export);
            }
        }
        if (isOpenAgentApi) {
            permissionList.add(SpaceObjectPermissionEnum.AgentApi);
        }
        if (isOpenAgentTempChat) {
            permissionList.add(SpaceObjectPermissionEnum.TempChat);
        }
        return permissionList;
    }

    public enum SpaceObjectPermissionEnum {
        Transfer,
        Delete,
        Copy,
        Publish,
        TempChat,
        AgentApi,
        Export,
    }
}
