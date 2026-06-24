package com.xspaceagi.agent.core.adapter.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SelectDto implements Serializable {

     @Schema(description =  "选项名称")
    private String name;

     @Schema(description =  "选项值")
    private String value;

     @Schema(description =  "是否默认")
    private Boolean isDefault;

     @Schema(description =  "下级选项")
    private List<SelectDto> selectList;
}
