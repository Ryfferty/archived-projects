package com.xspaceagi.system.application.dto;

import com.xspaceagi.system.infra.dao.entity.NotifyMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendNotifyMessageDto implements Serializable {
    private Long tenantId;
    @Schema(description = "发送者ID", hidden = true)
    private Long senderId;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "消息类型, Broadcast时可以不传userIds")
    private NotifyMessage.MessageScope scope;

    @Schema(description = "接收者ID列表")
    private List<Long> userIds;
}
