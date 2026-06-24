package com.xspaceagi.agent.core.adapter.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EventConfigDto implements Serializable {

    @Schema(description = "事件名称")
    private String name;

    @Schema(description = "事件标识")
    private String identification;

    @Schema(description = "事件类型, Link 外链；Page 扩展页面")
    private EventType type;

    @Schema(description = "链接地址，type类型为Link时有效")
    private String url;

    @Schema(description = "页面ID，type类型为Page时有效，更新时需要传入")
    private Long pageId;

    @Schema(description = "页面基础路径，type类型为Page时有效，不需要传入")
    private String basePath;

    @Schema(description = "页面名称，type类型为Page时有效")
    private String pageName;

    @Schema(description = "页面路径，type类型为Page时有效，更新时需要传入")
    private String pageUri;

    @Schema(description = "页面URL，type类型为Page时有效，不需要传入")
    private String pageUrl;

    @Schema(description = "参数配置")
    private List<Arg> args;

    @Schema(description = "参数配置JSON Schema，前端生成事件提示词时使用")
    private String argJsonSchema;

    public enum EventType {
        Link,
        Page,
    }
}
