package com.xspaceagi.agent.core.adapter.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemoryDto implements Serializable {

    private String topic;

    private String content;

    private String date;
}
