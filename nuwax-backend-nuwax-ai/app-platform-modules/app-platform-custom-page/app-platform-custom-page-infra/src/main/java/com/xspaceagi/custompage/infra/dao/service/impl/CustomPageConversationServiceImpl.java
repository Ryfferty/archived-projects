package com.xspaceagi.custompage.infra.dao.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.custompage.infra.dao.entity.CustomPageConversation;
import com.xspaceagi.custompage.infra.dao.mapper.CustomPageConversationMapper;
import com.xspaceagi.custompage.infra.dao.service.ICustomPageConversationService;

@Service
public class CustomPageConversationServiceImpl extends ServiceImpl<CustomPageConversationMapper, CustomPageConversation>
        implements ICustomPageConversationService {
}
