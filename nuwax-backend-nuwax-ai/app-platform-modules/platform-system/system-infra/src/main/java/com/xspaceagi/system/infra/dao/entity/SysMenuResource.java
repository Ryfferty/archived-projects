package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.system.spec.enums.BindTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * 菜单资源关联
 */
@Data
@TableName(value = "sys_menu_resource", autoResultMap = true)
public class SysMenuResource {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 资源ID
     */
    private Long resourceId;

    /**
     * 资源绑定类型 0:未绑定 1:全部绑定 2:部分绑定
     * @see BindTypeEnum
     */
    private Integer resourceBindType;
    
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

