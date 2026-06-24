package com.xspaceagi.knowledge.domain.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChatModelDto implements Serializable {

     @Schema(description =  "模型配置ID")
    private Long modelId;

     @Schema(description =  "模型名称")
    private String modelName;
}
