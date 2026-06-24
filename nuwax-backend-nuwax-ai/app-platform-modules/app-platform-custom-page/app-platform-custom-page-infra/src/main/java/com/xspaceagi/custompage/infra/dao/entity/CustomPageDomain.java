package com.xspaceagi.custompage.infra.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@TableName(value = "custom_page_domain")
@Data
public class CustomPageDomain {

    private Long id;

    @TableField(value = "_tenant_id")
    private Long tenantId;

    @TableField(value = "project_id")
    private Long projectId;

    private String domain;

    private Date created;

    private Date modified;
}
