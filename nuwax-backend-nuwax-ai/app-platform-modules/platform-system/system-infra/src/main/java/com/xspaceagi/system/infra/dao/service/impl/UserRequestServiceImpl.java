package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.UserReq;
import com.xspaceagi.system.infra.dao.mapper.UserReqMapper;
import com.xspaceagi.system.infra.dao.service.UserRequestService;
import com.xspaceagi.system.spec.tenant.thread.TenantFunctions;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRequestServiceImpl extends ServiceImpl<UserReqMapper, UserReq> implements UserRequestService {

    @Resource
    private UserReqMapper userReqMapper;

    @Override
    @Transactional
    public void addUserRequest(List<UserReq> userRequestList) {
        TenantFunctions.runWithIgnoreCheck(() -> userRequestList.forEach(userRequest -> userReqMapper.insertOrUpdateToday(userRequest.getTenantId(), userRequest.getUserId())));
    }
}
