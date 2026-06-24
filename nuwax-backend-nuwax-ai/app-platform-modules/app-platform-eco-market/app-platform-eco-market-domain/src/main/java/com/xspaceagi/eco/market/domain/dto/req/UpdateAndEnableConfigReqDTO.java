package com.xspaceagi.eco.market.domain.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新并启用配置请求DTO
 */
@Data
@Schema(description = "更新并启用配置请求DTO")
public class UpdateAndEnableConfigReqDTO {

    /**
     * 配置唯一标识
     */
    @Schema(description = "配置唯一标识", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;

    /**
     * 配置参数JSON
     */
    @Schema(description = "配置参数JSON", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String configParamJson;


    /**
     * 配置json,存储插件的配置信息如果有其他额外的信息保存放这里
     */
    @Schema(description = "配置json,存储插件的配置信息如果有其他额外的信息保存放这里", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String configJson;
} 