package com.xspaceagi.system.spec.tenant.thread;

import lombok.Data;

import java.util.Objects;

@Data
public class TenantSqlContext {

    /**
     * 是否忽略检查
     */
    private boolean ignoreCheck = false;

    private static final ThreadLocal<TenantSqlContext> holder = new ThreadLocal<>();

    public static TenantSqlContext get() {
        TenantSqlContext context = holder.get();
        if (Objects.isNull(context)) {
            context = new TenantSqlContext();
            holder.set(context);
        }
        return context;
    }

    public static void set(TenantSqlContext context) {
        holder.set(context);
    }

    public static void clean() {
        holder.remove();
    }

}
