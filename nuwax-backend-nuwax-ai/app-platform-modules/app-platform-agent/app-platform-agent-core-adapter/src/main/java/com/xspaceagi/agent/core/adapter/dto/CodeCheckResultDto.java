package com.xspaceagi.agent.core.adapter.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeCheckResultDto {

    @JsonPropertyDescription("是否通过检测，只要没有违反Goals中列出的范围，就通过并返回true")
    private Boolean pass;

    @JsonPropertyDescription("通过或不通过的原因")
    private String reason;
}
