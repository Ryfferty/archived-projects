package com.xspaceagi.system.sdk.retry.aop;

import com.alibaba.fastjson2.JSONObject;
import com.xspaceagi.system.sdk.retry.annotation.Retry;
import com.xspaceagi.system.sdk.retry.context.RetryContext;
import com.xspaceagi.system.sdk.retry.dto.RetrySubmission;
import com.xspaceagi.system.sdk.retry.dto.convert.SubmissionConverter;
import com.xspaceagi.system.sdk.retry.exception.RetryException;
import com.xspaceagi.system.sdk.retry.server.IRetrySubmitService;
import com.xspaceagi.system.sdk.retry.thread.CallerRunLogPolicy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
public class RetryHandler {

    @Resource
    private IRetrySubmitService retrySubmitService;

    private final static ExecutorService retryExecuteService = new ThreadPoolExecutor(
            1,
            200,
            60L,
            TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new CallerRunLogPolicy());

    @Around(value = "@annotation(retry)")
    public Object aroundCut(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        try {
            //entry
            RetryContext.entry();
            if (retry.async() && !RetryContext.get().isFormRetry()) {
                retryExecuteService.execute(new RetryRunnable(() -> {
                    try {
                        processCut(joinPoint, retry);
                    } catch (Throwable e) {
                        //异步处理不往上抛出异常
                    }
                }));
                return null;
            } else {
                return processCut(joinPoint, retry);
            }
        } finally {
            //quit
            RetryContext.quit();
        }
    }

    private Object processCut(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            log.warn("方法调用异常，重试信息提报, methodName={}", joinPoint.getSignature().getName());
            RetryContext retryContext = RetryContext.get();
            if (retryContext.isFormRetry()) {
                // 来自重试调用的抛异常,解决嵌套注解问题
                throw new RetryException(e);
            }
            reportData(joinPoint, retry, e);
            if (retry.throwException()) {
                throw e;
            }
        }
        return obj;
    }

    private void reportData(ProceedingJoinPoint joinPoint, Retry retry, Throwable e) {
        RetrySubmission retrySubmission = SubmissionConverter.convertFrom(joinPoint, retry, e);
        //RPC方式上报异常，后续增加MQ
        rpcReport(retrySubmission);
    }

    private void rpcReport(RetrySubmission submission) {
        log.info("触发rpc上报重试系统");
        try {
            retrySubmitService.submitRetryData(submission);
        } catch (Throwable e) {
            //日志作为最终兜底
            log.error("rpc重试数据上报失败: {}", JSONObject.toJSONString(submission), e);
        }
    }
}
