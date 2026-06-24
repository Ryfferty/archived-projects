package com.xspaceagi.agent.core.adapter.dto;

import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PublishApplySubmitDto implements Serializable {

    @Schema(description = "发布目标类型：目前可发布的类型有：Agent,Plugin,Workflow")
    private Published.TargetType targetType;

    @Schema(description = "发布目标ID，例如智能体ID；工作流ID；插件ID")
    private Long targetId;

    @Schema(description = "发布记录")
    private String remark;

    @Schema(description = "发布分类")
    private String category;

    @Schema(description = "发布项")
    private List<PublishItem> items;

    @Data
    public static class PublishItem {

        @Schema(description = "发布范围，可选范围：Space 空间,Tenant 系统")
        private Published.PublishScope scope;

        @Schema(description = "发布空间ID")
        private Long spaceId;

        @Schema(description = "是否允许复制,0不允许；1允许")
        private Integer allowCopy;

        @Schema(description = "仅展示模板, 0 否，1 是")
        private Integer onlyTemplate;
    }
}
