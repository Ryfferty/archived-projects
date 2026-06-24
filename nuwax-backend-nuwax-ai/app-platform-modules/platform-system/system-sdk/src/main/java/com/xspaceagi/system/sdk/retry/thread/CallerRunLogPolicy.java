package com.xspaceagi.system.sdk.retry.thread;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallerRunLogPolicy extends CallerRunsPolicy {


    @Override
    public void rejectedExecution(final Runnable r, final ThreadPoolExecutor e) {
        String msg = String.format(
            "Thread[Retry] pool is EXHAUSTED!  Pool Size: %d (active: %d, core: %d, max: %d ),execute it on main thread",
            e.getPoolSize(),
            e.getActiveCount(),
            e.getCorePoolSize(),
            e.getMaximumPoolSize()
        );
        log.warn(msg);
        super.rejectedExecution(r, e);
    }
}
