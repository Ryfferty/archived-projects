package com.xspaceagi.compose.ui.base;

import com.xspaceagi.system.spec.common.RequestContext;
import com.xspaceagi.system.spec.common.UserContext;
import com.xspaceagi.system.application.dto.UserDto;
import com.xspaceagi.system.infra.dao.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseController {


    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public UserContext getUser() {
        var userDto = (UserDto) RequestContext.get().getUser();
        return UserContext.builder()
                .userId(userDto.getId())
                .userName(userDto.getUserName())
                .nickName(userDto.getNickName())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .status(userDto.getStatus() == User.Status.Enabled ? 1 : -1)
                .tenantId(userDto.getTenantId())
                .tenantName(null)
                .orgId(null)
                .orgName(null)
                .roleType(null)
                .build();
    }

}
