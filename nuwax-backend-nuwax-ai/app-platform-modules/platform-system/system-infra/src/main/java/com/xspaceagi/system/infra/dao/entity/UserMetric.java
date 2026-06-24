package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.system.spec.enums.PeriodTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("user_metric")
public class UserMetric {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "_tenant_id")
    private Long tenantId;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "biz_type")
    private String bizType;

    @TableField(value = "period_type")
    private String periodType;

    @TableField(value = "period")
    private String period;

    @TableField(value = "value")
    private BigDecimal value;

    private Date modified;

    private Date created;
}