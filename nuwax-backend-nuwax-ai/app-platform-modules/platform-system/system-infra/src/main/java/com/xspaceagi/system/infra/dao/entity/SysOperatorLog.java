package com.xspaceagi.system.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志(SysOperatorLog)实体类
 *
 * @author p1
 * @since 2024-11-01 11:16:02
 */
@Data
@TableName("sys_operator_log")
public class SysOperatorLog {
    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "_tenant_id")
    private Long tenantId;
    /**
     * 1:操作类型;2:访问日志
     */
    private Long operateType;
    /**
     * 系统编码
     */
    private String systemCode;
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * 操作对象,比如:用户表,角色表,菜单表
     */
    private String objectOp;
    /**
     * 操作动作,比如:新增,删除,修改,查看
     */
    private String action;
    /**
     * 操作内容,比如评估页面
     */
    private String operateContent;
    /**
     * 额外的操作内容信息记录,比如:更新提交的数据内容
     */
    private String extraContent;
    /**
     * 操作人所属机构id
     */
    private Long orgId;
    /**
     * 操作人所属机构名称
     */
    private String orgName;
    /**
     * 创建人id
     */
    private Long creatorId;
    /**
     * 创建人名称
     */
    private String creator;
    /**
     * 创建时间
     */
    private LocalDateTime created;
    /**
     * 修改时间
     */
    private LocalDateTime modified;
    /**
     * 是否有效；1：有效，-1：无效
     */
    private Long yn;
}

