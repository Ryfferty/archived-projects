package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户和组关联
 */
@Data
@TableName(value = "sys_user_group", autoResultMap = true)
public class SysUserGroup {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 组ID
     */
    private Long groupId;
    
    /**
     * 租户ID
     */
    @TableField(value = "_tenant_id")
    private Long tenantId;
    
    /**
     * 创建人ID
     */
    private Long creatorId;
    
    /**
     * 创建人
     */
    private String creator;
    
    /**
     * 创建时间
     */
    private Date created;
    
    /**
     * 修改人ID
     */
    private Long modifierId;
    
    /**
     * 修改人
     */
    private String modifier;
    
    /**
     * 修改时间
     */
    private Date modified;
    
    /**
     * 是否有效；1：有效，-1：无效
     */
    private Integer yn;
}

