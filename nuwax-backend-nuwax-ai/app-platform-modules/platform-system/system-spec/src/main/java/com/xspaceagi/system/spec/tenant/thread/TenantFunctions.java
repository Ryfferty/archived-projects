package com.xspaceagi.system.spec.tenant.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
public class TenantFunctions {

    public static <V> V callWithIgnoreCheck(Callable<V> callable) {
        TenantSqlContext.get().setIgnoreCheck(true);
        V call;
        try {
            call = callable.call();
        } catch (Exception e) {
            log.error("callWithIgnoreCheck error", e);
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        } finally {
            TenantSqlContext.clean();
        }
        return call;
    }

    public static void runWithIgnoreCheck(Runnable runnable) {
        TenantSqlContext.get().setIgnoreCheck(true);
        try {
            runnable.run();
        } finally {
            TenantSqlContext.clean();
        }
    }


}
