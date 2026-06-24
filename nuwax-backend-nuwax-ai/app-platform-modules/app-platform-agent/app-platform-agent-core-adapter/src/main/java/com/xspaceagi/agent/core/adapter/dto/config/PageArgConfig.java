package com.xspaceagi.agent.core.adapter.dto.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageArgConfig implements Serializable {

    @Schema(description = "页面ID")
    private Long pageId;

    @Schema(description = "页面基础路径")
    private String basePath;

    @Schema(description = "页面路径，例如 /view")
    private String pageUri;

    @Schema(description = "页面名称")
    private String name;

    @Schema(description = "页面描述")
    private String description;

    @Schema(description = "参数配置")
    private List<Arg> args;

    public String getPageUrl(Long agentId) {
        if (basePath == null) {
            basePath = "";
        }
        if (pageUri == null) {
            pageUri = "";
        }
        return String.format("/page/%s-%s/prod/%s", basePath.startsWith("/") ? basePath.substring(1) : basePath, agentId, pageUri.startsWith("/") ? pageUri.substring(1) : pageUri);
    }
}