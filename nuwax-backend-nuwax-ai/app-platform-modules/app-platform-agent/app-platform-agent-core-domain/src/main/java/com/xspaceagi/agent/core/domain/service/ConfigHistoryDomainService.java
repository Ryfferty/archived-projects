package com.xspaceagi.agent.core.domain.service;

import com.xspaceagi.agent.core.adapter.repository.entity.ConfigHistory;

public interface ConfigHistoryDomainService {

    void addConfigHistory(ConfigHistory configHistory);
}
