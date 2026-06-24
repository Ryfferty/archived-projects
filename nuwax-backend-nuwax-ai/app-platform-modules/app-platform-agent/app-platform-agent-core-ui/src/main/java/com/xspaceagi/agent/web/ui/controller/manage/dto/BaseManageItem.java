package com.xspaceagi.agent.web.ui.controller.manage.dto;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import com.xspaceagi.custompage.sdk.dto.PublishTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "基础管理项")
public class BaseManageItem {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "代理ID")
    private Long agentId;

    @Schema(description = "空间ID")
    private Long spaceId;

    @Schema(description = "创建人名称")
    private String creatorName;

    @Schema(description = "创建时间")
    private Date created;

    @Schema(description = "操作标识（前端用于判断显示哪些操作按钮）")
    private String operation;

    @Schema(description = "发布状态")
    private Published.PublishStatus publishStatus;

    @Schema(description = "发布范围")
    private Published.PublishScope publishScope;

    @Schema(description = "是否受后台权限控制，0 不受，1 受")
    private Integer accessControl;

    @Schema(description = "网页应用的发布类型")
    private PublishTypeEnum publishType;

    @Schema(description = "网页应用绑定的智能体ID")
    private Long pageAgentId;
}