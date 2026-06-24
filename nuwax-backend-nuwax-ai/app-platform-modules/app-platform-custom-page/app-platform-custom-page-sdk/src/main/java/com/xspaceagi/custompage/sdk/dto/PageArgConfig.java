package com.xspaceagi.custompage.sdk.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageArgConfig {
    // 页面路径，例如 /view
    private String pageUri;
    private String name;
    private String description;
    // 参数配置
    private List<PageArg> args;
}