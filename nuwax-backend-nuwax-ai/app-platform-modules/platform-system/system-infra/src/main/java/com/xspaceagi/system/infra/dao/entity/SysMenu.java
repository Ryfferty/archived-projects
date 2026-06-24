package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xspaceagi.system.spec.enums.OpenTypeEnum;
import com.xspaceagi.system.spec.enums.SourceEnum;
import lombok.Data;

import java.util.Date;

/**
 * 菜单
 */
@Data
@TableName(value = "sys_menu", autoResultMap = true)
public class SysMenu {
    
    /**
     * 菜单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 资源码，唯一标识
     */
    private String code;

    /**
     * 菜单名称
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
     * 访问路径/路由地址
     */
    private String path;

    /**
     * 打开方式
     * @see OpenTypeEnum
     */
    private Integer openType;
    
    /**
     * 图标
     */
    private String icon;
    
    /**
     * 排序
     */
    private Integer sortIndex;
    
    /**
     * 状态 1:启用 0:禁用
     */
    private Integer status;
    
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

