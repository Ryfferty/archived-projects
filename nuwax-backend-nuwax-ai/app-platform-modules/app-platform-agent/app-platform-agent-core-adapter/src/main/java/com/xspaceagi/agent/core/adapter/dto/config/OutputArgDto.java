package com.xspaceagi.agent.core.adapter.dto.config;

import com.xspaceagi.agent.core.spec.enums.DataTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class OutputArgDto implements Serializable {

     @Schema(description =  "字段key")
    private String key;

     @Schema(description =  "关联上文字段key")
    private String bindKey;

     @Schema(description =  "参数名称")
    private String name;

     @Schema(description =  "参数描述，用作API文档")
    private String description;

     @Schema(description =  "数据类型,   String, 文本;Number, 数字;Array, 数组;Dict, 字典")
    private DataTypeEnum dataType;
}
