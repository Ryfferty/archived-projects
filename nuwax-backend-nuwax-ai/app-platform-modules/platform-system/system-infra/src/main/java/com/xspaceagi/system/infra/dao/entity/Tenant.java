package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xspaceagi.system.spec.enums.TenantStatus;
import lombok.Data;

import java.util.Date;

@Data
public class Tenant {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String description;

    private TenantStatus status;

    private String domain;

    private String version;

    private Date created;
}
