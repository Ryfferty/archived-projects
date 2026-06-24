package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.NotifyMessage;
import com.xspaceagi.system.infra.dao.mapper.NotifyMessageMapper;
import com.xspaceagi.system.infra.dao.service.NotifyMessageService;
import org.springframework.stereotype.Service;

@Service
public class NotifyMessageServiceImpl extends ServiceImpl<NotifyMessageMapper, NotifyMessage> implements NotifyMessageService {
}
