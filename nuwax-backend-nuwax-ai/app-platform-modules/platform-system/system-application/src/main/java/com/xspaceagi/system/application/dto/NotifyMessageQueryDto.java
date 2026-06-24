package com.xspaceagi.system.application.dto;

import com.xspaceagi.system.infra.dao.entity.NotifyMessageUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class NotifyMessageQueryDto implements Serializable {
    @Schema(description = "用户ID", hidden = true)
    private Long userId;

    @Schema(description = "最后消息ID")
    private Long lastId;

    @Schema(description = "查询条数")
    private Integer size;

    @Schema(description = "消息状态")
    private NotifyMessageUser.ReadStatus readStatus;
}
