package com.xspaceagi.system.spec.tenant.thread;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 租户隔离,需要用封装的线程池,捕获租户id
 */
public class TenantThreadPoolExecutor implements ExecutorService {

    private final ExecutorService executor;

    public TenantThreadPoolExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return executor.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return executor.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return executor.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executor.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(new TenantCallable<>(task));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return executor.submit(new TenantRunnable(task), result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return executor.submit(new TenantRunnable(task));
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        List<TenantCallable<T>> wrappedTasks = tasks.stream().map(TenantCallable::new).collect(Collectors.toList());
        return executor.invokeAll(wrappedTasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
        List<TenantCallable<T>> wrappedTasks = tasks.stream().map(TenantCallable::new).collect(Collectors.toList());
        return executor.invokeAll(wrappedTasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        List<TenantCallable<T>> wrappedTasks = tasks.stream().map(TenantCallable::new).collect(Collectors.toList());
        return executor.invokeAny(wrappedTasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        List<TenantCallable<T>> wrappedTasks = tasks.stream().map(TenantCallable::new).collect(Collectors.toList());
        return executor.invokeAny(wrappedTasks, timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        executor.execute(new TenantRunnable(command));
    }
}
