package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.UserAccessKey;
import com.xspaceagi.system.infra.dao.mapper.UserAccessKeyMapper;
import com.xspaceagi.system.infra.dao.service.UserAccessKeyService;
import org.springframework.stereotype.Service;

@Service
public class UserAccessKeyServiceImpl extends ServiceImpl<UserAccessKeyMapper, UserAccessKey> implements UserAccessKeyService {
}
