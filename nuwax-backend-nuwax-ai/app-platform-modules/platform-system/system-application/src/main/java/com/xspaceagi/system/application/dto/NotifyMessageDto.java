package com.xspaceagi.system.application.dto;

import com.xspaceagi.system.infra.dao.entity.NotifyMessageUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyMessageDto implements Serializable {

    @Schema(description = "消息ID")
    private Long id;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "消息状态")
    private NotifyMessageUser.ReadStatus readStatus;

    @Schema(description = "发送者信息")
    private Sender sender;

    @Schema(description = "创建时间（消息发送时间）")
    private Date created;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Sender {
        private Long userId;
        private String userName;
        private String nickName;
        private String avatar;
    }
}
