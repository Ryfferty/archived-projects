package com.xspaceagi.sandbox.application.dto;

import lombok.Data;

@Data
public class SandboxBindInfoDto {

    private BindTargetType targetType;
    private Long targetId;
    private String targetName;

    public enum BindTargetType {
        User,
        Space
    }
}
