package com.xspaceagi.agent.core.infra.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.agent.core.adapter.repository.PublishedRepository;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.agent.core.infra.dao.mapper.PublishedMapper;
import org.springframework.stereotype.Service;

@Service
public class PublishedRepositoryImpl extends ServiceImpl<PublishedMapper, Published> implements PublishedRepository {
}
