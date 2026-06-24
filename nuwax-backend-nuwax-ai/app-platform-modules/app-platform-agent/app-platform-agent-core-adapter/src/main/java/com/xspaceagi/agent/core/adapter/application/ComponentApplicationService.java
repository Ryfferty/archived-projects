package com.xspaceagi.agent.core.adapter.application;

import java.util.List;

import com.xspaceagi.agent.core.adapter.dto.ComponentDto;

public interface ComponentApplicationService {

    List<ComponentDto> getComponentListBySpaceId(Long spaceId);
}
