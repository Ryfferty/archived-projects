package com.xspaceagi.custompage.sdk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VersionInfoDto implements Serializable {

    private Integer version;

    private String time;

    private String action;

    private Map<String, String> ext;
}