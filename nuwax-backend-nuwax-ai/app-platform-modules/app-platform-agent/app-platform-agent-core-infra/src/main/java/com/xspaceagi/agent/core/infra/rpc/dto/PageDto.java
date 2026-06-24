package com.xspaceagi.agent.core.infra.rpc.dto;

import java.io.Serializable;
import java.util.List;

import com.xspaceagi.agent.core.adapter.dto.config.PageArgConfig;
import com.xspaceagi.custompage.sdk.dto.DataSourceDto;
import com.xspaceagi.custompage.sdk.dto.SourceTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PageDto implements Serializable {

    private Long id;

    private Long spaceId;

    private String name;

    private String description;

    private String icon;

    // 自定义页面唯一标识
    private String basePath;

    @Schema(description = "页面参数配置")
    private List<PageArgConfig> pageArgConfigs;

    private List<DataSourceDto> dataSources;

    @Schema(description = "是否需要登录,true:需要,false:不需要")
    private Boolean needLogin;

    @Schema(description = "创建者ID")
    private Long creatorId;

    @Schema(description = "封面图片")
    private String coverImg;

    @Schema(description = "封面图片来源")
    private SourceTypeEnum coverImgSourceType;

    @Schema(description = "智能体ID")
    private Long devAgentId;
}
