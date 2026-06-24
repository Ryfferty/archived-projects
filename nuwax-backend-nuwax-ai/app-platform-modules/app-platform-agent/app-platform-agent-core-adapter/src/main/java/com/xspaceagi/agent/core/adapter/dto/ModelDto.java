package com.xspaceagi.agent.core.adapter.dto;

import com.xspaceagi.agent.core.spec.enums.ModelApiProtocolEnum;
import com.xspaceagi.agent.core.spec.enums.ModelTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ModelDto implements Serializable {

     @Schema(description =  "模型ID")
    private Long modelId;

     @Schema(description =  "模型标识")
    private String model;

     @Schema(description =  "模型接口地址")
    private String host;

     @Schema(description =  "模型APIKEY，数组")
    private List<String> apiKeys;

     @Schema(description =  "模型备注（名称）")
    private String remark;

     @Schema(description =  "最大支持token数")
    private Integer maxTokens;

     @Schema(description =  "模型类型")
    private ModelTypeEnum type;

     @Schema(description =  "模型调用接口协议")
    private ModelApiProtocolEnum apiProtocol;

     @Schema(description =  "向量维度，模型类型为 Embeddings 时有效")
    private Integer dimension;
}
