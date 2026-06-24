package com.xspaceagi.agent.core.adapter.dto;

import lombok.Data;

@Data
public class ExportTemplateDto {

    private String type;
    private String name;
    private Object templateConfig;
    private Long spaceId;
}
