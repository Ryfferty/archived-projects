package com.xspaceagi.agent.core.adapter.dto.config.bind;

import com.xspaceagi.agent.core.adapter.dto.config.EventConfigDto;
import lombok.Data;

import java.util.List;

@Data
public class EventBindConfigDto {

    private List<EventConfigDto> eventConfigs;
}
