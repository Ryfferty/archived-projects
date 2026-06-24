package com.xspaceagi.system.domain.log.record;


import com.alibaba.fastjson2.JSON;
import com.xspaceagi.system.domain.log.ILogRecordAlarm;
import com.xspaceagi.system.domain.log.LogPrintValueParser;
import com.xspaceagi.system.domain.log.LogRecordPrint;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;


@Slf4j
@Component
public class LogRecordPrintAspectSupport extends LogPrintValueParser {


    @Autowired(required = false)
    private ILogRecordAlarm logRecordAlarm;


    public Object printLog(ProceedingJoinPoint joinPoint, LogRecordPrint logRecordPrint) throws Throwable {
        Object[] args = getArgs(joinPoint);

        String content = logRecordPrint.content();

        MethodResult methodResult = new MethodResult(true, null, null);
        // 记录开始时间
        long startTime = System.currentTimeMillis();

        Object result = null;
        try {
            result = joinPoint.proceed();
            return result;
        } catch (Exception e) {
            methodResult = new MethodResult(false, e, e.getMessage());
            throw e;
        } finally {
            // 记录结束时间
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            if (methodResult.success) {
                if (log.isInfoEnabled()) {
                    log.info("LogRecordPrint-content={},耗时={} ms,请求={},结果={}",
                            content, executionTime, JSON.toJSONString(args), JSON.toJSONString(result));
                }
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("LogRecordPrint-content={},耗时={} ms,请求={},异常={}",
                            content, executionTime, JSON.toJSONString(args), methodResult.errorMsg);
                }
            }

            if (methodResult.throwable != null && logRecordPrint.sendAlarm()) {
                if (Objects.nonNull(logRecordAlarm)) {
                    //判断是否发送告警信息
                    String msg = String.format("请求=%s,错误信息=%s", JSON.toJSONString(args), methodResult.errorMsg);
                    logRecordAlarm.sendAlarm(logRecordPrint.alarmCode(), content, msg);
                } else {
                    log.info("[logRecordAlarm]bean为空,无法发送告警");
                }


            }

        }


    }


    private Object[] getArgs(final ProceedingJoinPoint joinPoint) {
        return joinPoint.getArgs();
    }


    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }


    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint
                        .getTarget()
                        .getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(),
                                method.getParameterTypes());
            } catch (SecurityException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return method;
    }


    static class MethodResult {

        private boolean success;
        private Throwable throwable;
        private String errorMsg;

        public MethodResult(boolean success, Throwable throwable, String errorMsg) {
            this.success = success;
            this.throwable = throwable;
            this.errorMsg = errorMsg;
        }
    }


}
