package com.xspaceagi.agent.web.ui.controller.dto;

import lombok.Data;

@Data
public class VerifyCodeCheckDto {
    private String phone;
    private String code;
}
