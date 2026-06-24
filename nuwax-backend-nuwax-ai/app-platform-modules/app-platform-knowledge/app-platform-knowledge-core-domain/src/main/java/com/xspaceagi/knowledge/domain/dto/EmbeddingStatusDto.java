package com.xspaceagi.knowledge.domain.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description  = "嵌入状态")
public class EmbeddingStatusDto implements Serializable {
     @Schema(description =  "Doc ID")
    private Long docId;
     @Schema(description =  "QA数量")
    private Long qaCount;
     @Schema(description =  "QA Embedding数量")
    private Long qaEmbeddingCount;
}
