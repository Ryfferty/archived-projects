package com.xspaceagi.knowledge.domain.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmbeddingModelDto implements Serializable {

     @Schema(description =  "嵌入模型ID")
    private Long embeddingId;

     @Schema(description =  "嵌入名称")
    private String name;
}
