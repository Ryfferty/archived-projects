package com.xspaceagi.system.infra.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.system.infra.dao.entity.UserReq;

import java.util.List;


public interface UserRequestService extends IService<UserReq> {
    void addUserRequest(List<UserReq> userRequestList);
}
