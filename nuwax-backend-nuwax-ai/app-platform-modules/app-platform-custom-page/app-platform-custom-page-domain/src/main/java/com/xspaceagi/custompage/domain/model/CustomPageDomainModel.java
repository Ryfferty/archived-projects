package com.xspaceagi.custompage.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class CustomPageDomainModel {

    private Long id;

    private Long tenantId;

    private Long projectId;

    private String domain;

    private Date created;

    private Date modified;
}
