package com.xspaceagi.agent.core.infra.code;

import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class CodeArgDto implements Serializable {

    @Schema(description = "参数")
    @JSONField(name = "json_param")
    private Map<String, Object> params;

    @Schema(description = "引擎类型,js或python", example = "js", requiredMode = Schema.RequiredMode.REQUIRED)
    @JSONField(name = "engine_type")
    private String engineType;

    @Schema(description = "代码，export default 开头，函数名可以自定义，export default async function xxx(params) {return {'p1':1,'p2':2};}", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "用户ID", hidden = true)
    @JSONField(name = "uid")
    private String userId;
}
