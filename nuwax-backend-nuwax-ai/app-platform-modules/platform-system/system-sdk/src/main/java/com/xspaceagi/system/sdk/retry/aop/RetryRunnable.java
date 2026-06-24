package com.xspaceagi.system.sdk.retry.aop;


import com.xspaceagi.system.sdk.retry.context.RetryContext;

public class RetryRunnable implements Runnable {

    private RetryContext context;

    private final Runnable runnable;

    public RetryRunnable(Runnable runnable) {
        this.context = RetryContext.get().deepCopy();
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try {
            RetryContext.set(context);
            runnable.run();
        } finally {
            RetryContext.clean();
        }
    }
}
