package com.xspaceagi.agent.core.adapter.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import java.util.List;

@Data
public class HostExtractDto {

    @JsonPropertyDescription("域名列表")
    private List<String> domains;
}
