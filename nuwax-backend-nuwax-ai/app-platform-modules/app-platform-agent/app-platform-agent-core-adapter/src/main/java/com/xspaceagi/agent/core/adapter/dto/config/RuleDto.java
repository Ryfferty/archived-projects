package com.xspaceagi.agent.core.adapter.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class RuleDto implements Serializable {

     @Schema(description =  "输入最大字符长度，null为不限制")
    private Integer maxLength;

     @Schema(description =  "输入最小值，null为不限制")
    private Integer minValue;

     @Schema(description =  "输入最大值，null为不限制")
    private Integer maxValue;

     @Schema(description =  "js校验脚本，null为不限制")
    private String script;
}
