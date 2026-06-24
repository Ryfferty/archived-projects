package com.xspaceagi.agent.core.adapter.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.agent.core.adapter.repository.entity.ConfigHistory;
import com.xspaceagi.agent.core.adapter.repository.entity.Published;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("agent_config_history")
public class ConfigHistoryDto {
    private Long id;
    private Published.TargetType targetType;
    private Long targetId;
    @Schema(description = "操作类型,Add 新增, Edit 编辑, Publish 发布")
    private ConfigHistory.Type type;

    @Schema(description = "当时的配置信息")
    private Object config;

    @Schema(description = "操作描述")
    private String description;

    @Schema(description = "操作人")
    private OpUser opUser;

    private Date modified;

    @Schema(description = "创建时间")
    private Date created;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OpUser {
        @Schema(description = "用户ID")
        private Long userId;

        @Schema(description = "用户名")
        private String userName;

        @Schema(description = "昵称")
        private String nickName;

        @Schema(description = "头像")
        private String avatar;
    }
}