package com.xspaceagi.system.domain.log.record;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xspaceagi.system.domain.log.LogConstants;
import com.xspaceagi.system.domain.log.LogPrint;
import com.xspaceagi.system.domain.log.LogPrintExpressionEvaluator;
import com.xspaceagi.system.domain.log.LogPrintValueParser;
import com.xspaceagi.system.spec.common.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class LogPrintAspectSupport extends LogPrintValueParser {

    public static final String underline = "_";
    private final static Logger LOGGER = LoggerFactory.getLogger(LogPrintAspectSupport.class);
    private final static ObjectMapper OM = new ObjectMapper();
    private final LogPrintExpressionEvaluator expressionEvaluator = new LogPrintExpressionEvaluator();


    public Object printLog(ProceedingJoinPoint joinPoint, LogPrint logPrint) throws Throwable {
        Class<?> targetClass = getTargetClass(joinPoint.getTarget());
        Method method = getMethod(joinPoint);
        Object[] args = getArgs(joinPoint);

        MethodResult methodResult = new MethodResult(true, null, null);

        final Map<String, String> expressionValueMap = new HashMap<>();
        try {
            Map<String, String> expressionMap = LogExpression.toMap(logPrint);

            EvaluationContext evaluationContext = expressionEvaluator.createEvaluationContext(method, args, targetClass,
                    this.beanFactory);

            expressionValueMap.put(LogConstants.STEP, logPrint.step());

            AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(method, targetClass);
            expressionMap.forEach((name, expression) -> {
                Object value = null;
                try {
                    value = expressionEvaluator.parseExpression(expression, annotatedElementKey, evaluationContext);
                } catch (Exception e) {
                    LOGGER.error("parse expression occur exception", e);
                }
                if (null == value) {
                    return;
                }
                if (value instanceof String) {
                    expressionValueMap.put(name, (String) value);
                } else {
                    try {
                        expressionValueMap.put(name, OM.writeValueAsString(value));
                    } catch (JsonProcessingException e) {
                        LOGGER.error("parse value occur exception", e);
                    }
                }
            });

            String logPrefix = getLogPrefix(expressionValueMap);


            // 增加日志统一前缀
            MDC.put(LogConstants.PREFIX, logPrefix);

            String content = expressionValueMap.get(LogConstants.CONTENT);
            if (StringUtils.isNotEmpty(content)) {
                LOGGER.info("content :{}", content);
            }


        } catch (Exception e) {
            // 此处异常不影响正常流程
            LOGGER.error("log record parse exception", e);
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            methodResult = new MethodResult(false, e, e.getMessage());
        } finally {
            // 删除mdc中的线程变量
            MDC.remove(LogConstants.PREFIX);
        }

        if (methodResult.throwable != null) {
            throw methodResult.throwable;
        }

        return result;
    }

    /**
     * 获取日志前缀
     *
     * @param expressionValueMap
     * @return
     */
    private String getLogPrefix(final Map<String, String> expressionValueMap) {
        //TODO 其它前缀拼接参数（待定）
        String localeId = expressionValueMap.get(LogConstants.LOCALE_ID);
        String billNo = expressionValueMap.get(LogConstants.BILL_NO);
        String step = expressionValueMap.get(LogConstants.STEP);
        String topic = expressionValueMap.get(LogConstants.TOPIC);
        String businessType = expressionValueMap.get(LogConstants.BUSINESS_TYPE);
        String messageId = expressionValueMap.get(LogConstants.MESSAGE_ID);

        StringBuilder prefixBuilder = new StringBuilder();

        boolean appended = false;
        if (StringUtils.isNotEmpty(topic)) {
            prefixBuilder.append("T[").append(topic).append("]");
            appended = true;
        }

        if (StringUtils.isNotEmpty(messageId)) {
            if (appended) {
                prefixBuilder.append(underline);
            }
            prefixBuilder.append("M[").append(messageId).append("]");
            appended = true;
        }

        Long tenantId = RequestContext.get().getTenantId();
        if (Objects.nonNull(tenantId)) {
            if (appended) {
                prefixBuilder.append(underline);
            }

            prefixBuilder.append("TE[").append(tenantId).append("]");
            appended = true;
        }

        if (StringUtils.isNotEmpty(localeId)) {
            if (appended) {
                prefixBuilder.append(underline);
            }

            prefixBuilder.append("L[").append(localeId).append("]");
            appended = true;
        }

        if (StringUtils.isNotEmpty(billNo)) {
            if (appended) {
                prefixBuilder.append(underline);
            }

            prefixBuilder.append("N[").append(tenantId).append("]");
            appended = true;
        }

        if (StringUtils.isNotEmpty(businessType)) {
            if (appended) {
                prefixBuilder.append(underline);
            }

            prefixBuilder.append("B[").append(businessType).append("]");
            appended = true;
        }

        if (StringUtils.isNotEmpty(step)) {
            if (appended) {
                prefixBuilder.append(underline);
            }
            prefixBuilder.append("S[").append(step).append("]");
        }

        return prefixBuilder.toString();
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


    static class LogExpression {


        private static Map<String, String> toMap(LogPrint logPrint) {
            Map<String, String> expressionMap = new HashMap<>();

            putIfValid(expressionMap, LogConstants.LOCALE_ID, logPrint.localeId());
            putIfValid(expressionMap, LogConstants.BILL_NO, logPrint.billNo());
            putIfValid(expressionMap, LogConstants.TOPIC, logPrint.topic());
            putIfValid(expressionMap, LogConstants.MESSAGE_ID, logPrint.messageId());
            putIfValid(expressionMap, LogConstants.BUSINESS_TYPE, logPrint.businessType());
            putIfValid(expressionMap, LogConstants.CONTENT, logPrint.content());

            return expressionMap;
        }


        private static void putIfValid(Map<String, String> expressionMap, String key, String value) {
            if (StringUtils.isEmpty(value)) {
                return;
            }

            expressionMap.put(key, value);
        }

    }


}
