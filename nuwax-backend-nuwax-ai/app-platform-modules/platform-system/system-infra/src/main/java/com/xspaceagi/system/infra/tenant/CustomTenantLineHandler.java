package com.xspaceagi.system.infra.tenant;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.xspaceagi.system.spec.common.RequestContext;
import com.xspaceagi.system.spec.tenant.thread.TenantSqlContext;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.util.Assert;

@AllArgsConstructor
public class CustomTenantLineHandler implements TenantLineHandler {


    private final MyTenantProperties properties;


    @Override
    public Expression getTenantId() {
        Assert.notNull(RequestContext.get(), "RequestContext must be non-null");
        Assert.notNull(RequestContext.get().getTenantId(), "tenantId must be non-null");
        return new LongValue(RequestContext.get().getTenantId());
    }

    @Override
    public boolean ignoreTable(String tableName) {

        if (TenantSqlContext.get().isIgnoreCheck()) {
            return true;
        }
        // 指定不需要添加过滤条件的表
        // 返回true表示忽略此表
        // 如果所有表都需要过滤条件，返回 false
        return properties.getIgnoreTenantTables().contains(tableName)
                || RequestContext.getTenantIgnoreTables().contains(tableName);
    }

    @Override
    public String getTenantIdColumn() {
        return properties.getColumn();
    }
}