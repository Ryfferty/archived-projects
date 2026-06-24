package com.xspaceagi.system.sdk.retry.context;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
public class RetryContext {

    /**
     * 是否是重试发起的调用
     */
    private boolean formRetry = false;

    private int counter = 0;

    /**
     * 额外信息
     */
    private Map<String, Object> ext = new HashMap<>();

    private void incrCounter() {
        counter++;
    }

    private void decrCounter() {
        counter--;
    }

    private boolean canRemove() {
        return counter <= 0;
    }

    private static final ThreadLocal<RetryContext> holder = new ThreadLocal<>();

    public static RetryContext get() {
        RetryContext context = holder.get();
        if (Objects.isNull(context)) {
            context = new RetryContext();
            holder.set(context);
        }
        return context;
    }

    public static void set(RetryContext context) {
        holder.set(context);
    }

    public static void clean() {
        holder.remove();
    }

    /**
     * 为重入定制 出入计数
     */
    public static void entry() {
        RetryContext retryContext = get();
        retryContext.incrCounter();
    }

    /**
     * 为重入定制 出入计数
     */
    public static void quit() {
        RetryContext retryContext = get();
        retryContext.decrCounter();
        if (retryContext.canRemove()) {
            clean();
        }
    }

    public static Map<String, Object> getExt() {
        RetryContext retryContext = get();
        return retryContext.ext;
    }

    public RetryContext deepCopy() {
        RetryContext context = new RetryContext();
        context.setFormRetry(this.formRetry);
        context.setExt(this.ext);
        return context;
    }
}
