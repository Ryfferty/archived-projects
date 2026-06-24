package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.NotifyMessageUser;
import com.xspaceagi.system.infra.dao.mapper.NotifyMessageUserMapper;
import com.xspaceagi.system.infra.dao.service.NotifyMessageUserService;
import org.springframework.stereotype.Service;

@Service
public class NotifyMessageUserServiceImpl extends ServiceImpl<NotifyMessageUserMapper, NotifyMessageUser> implements NotifyMessageUserService {
}
