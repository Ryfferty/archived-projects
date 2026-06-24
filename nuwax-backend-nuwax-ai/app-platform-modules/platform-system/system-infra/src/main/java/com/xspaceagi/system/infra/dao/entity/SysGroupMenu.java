package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.system.spec.enums.BindTypeEnum;
import lombok.Data;

import java.util.Date;

/**
 * 用户组和菜单关联
 */
@Data
@TableName(value = "sys_group_menu", autoResultMap = true)
public class SysGroupMenu {
    
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户组ID
     */
    private Long groupId;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 子菜单绑定类型 0:未绑定 1:全部绑定 2:部分绑定
     * @see BindTypeEnum
     */
    private Integer menuBindType;

    /**
     * 资源树JSON（包含每个节点的绑定类型）
     */
    @TableField(value = "resource_tree_json")
    private String resourceTreeJson;
    
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

