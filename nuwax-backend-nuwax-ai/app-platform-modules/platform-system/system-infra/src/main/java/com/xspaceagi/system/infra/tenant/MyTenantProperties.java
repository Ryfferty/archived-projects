package com.xspaceagi.system.infra.tenant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * mybatis-plus mysql租户插件
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "mybatis-plus.tenant")
public class MyTenantProperties {

    /**
     * 多租户字段名称
     * <p>默认 _tenant_id</p>
     */
    private String column = "_tenant_id";

    /**
     * 忽略的表
     */
    private List<String> ignoreTenantTables;

    /**
     * 默认开启
     */
    private boolean enabled = true;

}
