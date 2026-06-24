package com.xspaceagi.agent.core.adapter.dto;

import com.xspaceagi.agent.core.spec.enums.MessageTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto implements Message {

    private Long index;

    private Long tenantId;

    @Schema(description = "消息发送方类型, User、Agent")
    private SenderType senderType;

    @Schema(description = "消息发送方ID")
    private String senderId;

    @Schema(description = "关联用户ID")
    private Long userId;

    @Schema(description = "关联的agentID")
    private Long agentId;

    @Schema(description = "消息ID")
    private String id;

    @Schema(description = "assistant 模型回复；user 用户消息")
    private Role role;

    private MessageTypeEnum type;

    @Schema(description = "消息内容")
    private String text;

    @Schema(description = "消息时间")
    private Date time;

    @Schema(description = "消息附件")
    private List<AttachmentDto> attachments;

    @Schema(description = "思考内容")
    private String think;

    @Schema(description = "引用消息内容")
    private String quotedText;

    private boolean finished;

    private String finishReason;

    @Schema(description = "执行过程输出数据")
    private List<Object> componentExecutedList;

    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.valueOf(role.name());
    }

    public enum Role {
        USER,
        ASSISTANT,
        SYSTEM,
        FUNCTION,
    }

    public enum SenderType {
        USER,
        AGENT
    }
}
