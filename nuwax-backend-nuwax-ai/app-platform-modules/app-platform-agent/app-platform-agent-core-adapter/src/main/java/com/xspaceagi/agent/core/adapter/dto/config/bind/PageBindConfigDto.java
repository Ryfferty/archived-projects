package com.xspaceagi.agent.core.adapter.dto.config.bind;

import com.xspaceagi.agent.core.adapter.dto.config.PageArgConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageBindConfigDto implements Serializable {

    // 自定义页面唯一标识
    @Schema(description = "自定义页面唯一标识")
    private String basePath;

    @Schema(description = "页面参数配置")
    private List<PageArgConfig> pageArgConfigs;

    @Schema(description = "页面是否模型可见，1 可见，0 不可见")
    private Integer visibleToLLM;

    @Schema(description = "是否为智能体页面首页，1 为默认首页，0 不为首页")
    private Integer homeIndex;

    @Schema(description = "页面名称")
    private String pageName;

    @Schema(description = "页面图标")
    private String pageIcon;
}
