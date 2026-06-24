package com.xspaceagi.sandbox.infra.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 临时代理实体类
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "sandbox_proxy", autoResultMap = true)
public class SandboxProxy implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "_tenant_id")
    private Long tenantId;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "sandbox_id")
    private Long sandboxId;

    @TableField(value = "proxy_key")
    private String proxyKey;

    @TableField(value = "backend_host")
    private String backendHost;

    @TableField(value = "backend_port")
    private Integer backendPort;

    @TableField(value = "created")
    private Date created;

    @TableField(value = "modified")
    private Date modified;
}