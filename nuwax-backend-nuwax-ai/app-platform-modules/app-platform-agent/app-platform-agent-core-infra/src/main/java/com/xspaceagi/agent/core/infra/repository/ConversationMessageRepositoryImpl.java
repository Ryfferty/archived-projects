package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.ConversationMessageRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.ConversationMessage;
import com.xspaceagi.agent.core.infra.dao.mapper.ConversationMessageMapper;
import org.springframework.stereotype.Service;

@Service
public class ConversationMessageRepositoryImpl extends ServiceImpl<ConversationMessageMapper, ConversationMessage> implements ConversationMessageRepository {
}
