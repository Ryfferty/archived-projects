package com.xspaceagi.agent.core.adapter.application;

import com.xspaceagi.agent.core.adapter.dto.ConfigHistoryDto;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;

import java.util.List;

public interface ConfigHistoryApplicationService {

    List<ConfigHistoryDto> queryConfigHistoryList(Published.TargetType targetType, Long targetId);


    ConfigHistoryDto queryConfigHistory(Long id);
}
