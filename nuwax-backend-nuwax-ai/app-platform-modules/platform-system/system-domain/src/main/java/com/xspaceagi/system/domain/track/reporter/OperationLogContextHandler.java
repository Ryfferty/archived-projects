package com.xspaceagi.system.domain.track.reporter;

import java.util.Optional;

public class OperationLogContextHandler {

    private static final ThreadLocal<OperationLogContext> threadLocal = new ThreadLocal<>();


    public static void set(OperationLogContext context) {
        threadLocal.set(context);
    }

    public static Optional<OperationLogContext> get() {
        return Optional.ofNullable(threadLocal.get());
    }

    public static void remove() {
        threadLocal.remove();
    }
}
