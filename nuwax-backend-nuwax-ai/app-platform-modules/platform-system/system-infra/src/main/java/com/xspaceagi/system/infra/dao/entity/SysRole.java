package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.system.spec.enums.SourceEnum;
import com.xspaceagi.system.spec.enums.StatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * 角色
 */
@Data
@TableName(value = "sys_role", autoResultMap = true)
public class SysRole {
    
    /**
     * 角色ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 编码
     */
    private String code;
    
    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;
    
    /**
     * 来源
     * @see SourceEnum
     */
    private Integer source;
    
    /**
     * 状态
     * @see StatusEnum
     */
    private Integer status;
    
    /**
     * 排序
     */
    private Integer sortIndex;
    
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

