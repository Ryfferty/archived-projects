package com.xspaceagi.agent.core.adapter.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.agent.core.spec.enums.PluginTypeEnum;

import com.xspaceagi.system.application.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PublishApplyDto implements Serializable {
    private Long id;

    @Schema(description = "所属空间ID")
    private Long spaceId;

    @Schema(description = "目标对象类型")
    private Published.TargetType targetType;

    @Schema(description = "子类型")
    private Published.TargetSubType targetSubType;

    @Schema(description = "目标对象ID，智能体ID、插件ID、工作流ID")
    private Long targetId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "扩展字段")
    private Object ext;

    //该字段已没有实际作用，后续弃用
    @Schema(description = "发布渠道")
    private List<Published.PublishChannel> channels;

    private Published.PublishScope scope;

    @Schema(description = "发布状态")
    private Published.PublishStatus publishStatus;

    @Schema(description = "目标配置", hidden = true)
    private Object targetConfig;

    @Schema(description = "发布记录")
    private String remark;

    @Schema(description = "发布修改时间")
    private Date modified;

    @Schema(description = "发布创建时间")
    private Date created;

    @Schema(description = "发布申请用户")
    private UserDto applyUser;

    private PluginTypeEnum pluginType;

    private Integer allowCopy;

    private Integer onlyTemplate;

    private String category;
}
