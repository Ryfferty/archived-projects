package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReq {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "_tenant_id")
    private Long tenantId;
    private Long userId;
    private String dt;
    private Long reqCount;
    private Date modified;
    private Date created;
}
