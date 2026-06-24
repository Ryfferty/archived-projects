package com.xspaceagi.custompage.sdk.dto;

import lombok.Data;

@Data
public class PageArg {
    // 参数key，唯一标识，不需要前端传递，后台根据配置自动生成
    private String key;
    // 参数名称，符合函数命名规则
    private String name;
    // 参数详细描述信息
    private String description;
    // 数据类型
    private DataTypeEnum dataType;
    // 是否必须
    private boolean require;

    //是否开启（对模型可见，默认开启）
    private Boolean enable;

    //默认值
    private String bindValue;

    private InputTypeEnum inputType;
}
