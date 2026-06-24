package com.xspaceagi.agent.core.spec.enums;

public enum MessageTypeEnum {

//    USER("user"),
//
//    ASSISTANT("assistant"),
//
//    SYSTEM("system"),
//
//    TOOL("tool"),

    CHAT("chat"),
    THINK("think"),
    PROCESS_OUTPUT("process_output"),
    QUESTION("question"),
    ANSWER("answer");

    private final String value;

    MessageTypeEnum(String value) {
        this.value = value;
    }

    public static MessageTypeEnum fromValue(String value) {
        for (MessageTypeEnum messageType : MessageTypeEnum.values()) {
            if (messageType.getValue().equals(value)) {
                return messageType;
            }
        }
        throw new IllegalArgumentException("Invalid MessageType value: " + value);
    }

    public String getValue() {
        return this.value;
    }

}