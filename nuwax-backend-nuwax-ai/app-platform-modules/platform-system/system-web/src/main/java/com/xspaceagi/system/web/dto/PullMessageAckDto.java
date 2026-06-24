package com.xspaceagi.system.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class PullMessageAckDto {

    private String clientId;
    private String clientSecret;
    private List<String> messageIds;
    private String uid;
}