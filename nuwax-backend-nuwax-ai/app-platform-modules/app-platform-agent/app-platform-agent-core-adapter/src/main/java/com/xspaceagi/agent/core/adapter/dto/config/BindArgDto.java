package com.xspaceagi.agent.core.adapter.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class BindArgDto implements Serializable {

     @Schema(description =  "参数名称")
    private String name;

     @Schema(description =  "参数key")
    private String key;

     @Schema(description =  "绑定参数key")
    private String bindKey;
}
