package com.xspaceagi.agent.core.infra.component.table.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TableResponseDto {

    private Integer romNum;
    private Long id;
    private boolean success;
    private List<Map<String, Object>> outputList;
}
