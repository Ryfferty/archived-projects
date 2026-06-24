package com.xspaceagi.agent.core.infra.component.model.strategy;

import com.xspaceagi.agent.core.adapter.dto.config.ModelConfigDto;

public interface ApiSelectStrategy {

    ModelConfigDto.ApiInfo selectApi(ModelConfigDto modelConfigDto);
}
