package com.xspaceagi.agent.web.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class StreamDto implements Serializable {

     @Schema(description =  "消息ID，用于识别一条关联消息")
    private String id;

     @Schema(description =  "事件，chat-msg 会话消息；keepAlive 保活消息")
    private String event;

     @Schema(description =  "消息数据（有可能只是消息的一部分）")
    private String data;
}
