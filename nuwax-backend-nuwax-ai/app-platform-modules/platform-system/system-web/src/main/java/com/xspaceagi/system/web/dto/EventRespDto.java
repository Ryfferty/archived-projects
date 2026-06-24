package com.xspaceagi.system.web.dto;

import com.xspaceagi.system.application.dto.EventDto;
import lombok.Data;

import java.util.List;

@Data
public class EventRespDto {

    private String version;

    private boolean hasEvent;

    private List<EventDto<?>> eventList;
}
