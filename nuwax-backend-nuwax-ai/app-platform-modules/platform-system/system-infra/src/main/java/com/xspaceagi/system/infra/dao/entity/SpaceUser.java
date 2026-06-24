package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("space_user")
public class SpaceUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "_tenant_id")
    private Long tenantId;
    private Long spaceId;
    private Long userId;
    private Role role;
    private Date modified;
    private Date created;

    public enum Role {
        Owner,
        Admin,
        User
    }
}
