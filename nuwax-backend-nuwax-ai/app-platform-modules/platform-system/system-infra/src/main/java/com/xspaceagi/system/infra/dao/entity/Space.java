package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("space")
public class Space {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "_tenant_id")
    private Long tenantId;
    private String name;
    private String description;
    private String icon;
    private Type type;
    private Long creatorId;
    private Integer yn;
    private Date modified;
    private Date created;
    private Integer receivePublish;
    private Integer allowDevelop;
    public enum Type {
        Personal,
        Team,
        Class
    }

}
