package com.xspaceagi.system.infra.tenant;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(MyTenantProperties.class)
@ConditionalOnProperty(prefix = "mybatis-plus.tenant.db", value = "enabled", havingValue = "true", matchIfMissing = true)
public class MybatisTenantAutoConfiguration {

    @Bean
    @Primary
    public TenantLineHandler tenantHandler(MyTenantProperties tenantProperties) {
        return new CustomTenantLineHandler(tenantProperties);
    }

    @Bean
    @Primary
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantLineHandler tenantHandler) {
        TenantLineInnerInterceptor tenantInterceptor = new TenantLineInnerInterceptor();
        tenantInterceptor.setTenantLineHandler(tenantHandler);
        return tenantInterceptor;
    }

}
