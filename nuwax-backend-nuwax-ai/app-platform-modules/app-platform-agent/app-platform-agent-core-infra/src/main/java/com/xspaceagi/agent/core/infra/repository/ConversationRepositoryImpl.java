package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.ConversationRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.Conversation;
import com.xspaceagi.agent.core.infra.dao.mapper.ConversationMapper;
import org.springframework.stereotype.Service;

@Service
public class ConversationRepositoryImpl extends ServiceImpl<ConversationMapper, Conversation> implements ConversationRepository {
}
