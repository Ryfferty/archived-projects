package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.CardRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.Card;
import com.xspaceagi.agent.core.infra.dao.mapper.CardMapper;
import org.springframework.stereotype.Service;

@Service
public class CardRepositoryImpl extends ServiceImpl<CardMapper, Card> implements CardRepository {
}
