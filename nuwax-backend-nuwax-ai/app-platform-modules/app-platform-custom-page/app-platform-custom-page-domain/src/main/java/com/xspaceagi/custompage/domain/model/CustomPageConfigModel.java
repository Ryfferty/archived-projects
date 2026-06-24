package com.xspaceagi.custompage.domain.model;

import java.util.Date;
import java.util.List;

import com.xspaceagi.custompage.sdk.dto.*;

import lombok.Data;

@Data
public class CustomPageConfigModel {

    private Long id;

    private String name;

    private String description;

    private String icon;

    private String coverImg;

    private SourceTypeEnum coverImgSourceType;

    // 自定义页面唯一标识
    private String basePath;

    // 1:运行中
    private Integer buildRunning;

    // 发布类型
    private PublishTypeEnum publishType;

    // 需要登录,1:需要
    private Integer needLogin;

    // 调试关联智能体
    private Long devAgentId;

    // 页面项目类型
    private ProjectType projectType;

    private List<ProxyConfig> proxyConfigs;

    private List<PageArgConfig> pageArgConfigs;

    private List<DataSourceDto> dataSources;

    private Long sandboxId;

    private Object ext;

    private Long tenantId;

    private Long spaceId;

    private Date created;
    private Long creatorId;
    private String creatorName;
    private Date modified;
    private Long modifiedId;
    private String modifiedName;

    private Integer yn;
}