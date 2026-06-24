package com.xspaceagi.system.sdk.retry.dto.convert;

import com.xspaceagi.system.sdk.retry.annotation.Retry;
import com.xspaceagi.system.sdk.retry.constant.CsConstants;
import com.xspaceagi.system.sdk.retry.context.RetryContext;
import com.xspaceagi.system.sdk.retry.dto.RetrySubmission;
import com.xspaceagi.system.sdk.retry.utils.ExceptionUtils;
import com.xspaceagi.system.sdk.retry.utils.GeneratorUtils;
import com.xspaceagi.system.spec.jackson.JsonSerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;

import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
public final class SubmissionConverter {

    public static RetrySubmission convertFrom(ProceedingJoinPoint joinPoint,
                                              Retry retry,
                                              Throwable e) {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();

        RetrySubmission retrySubmission = new RetrySubmission();
        //application info
        retrySubmission.setProjectCode("projectCode");
        retrySubmission.setAppCode("appCode");

        //tid
        String tid = MDC.get("tid");
        if (StringUtils.isBlank(tid)) {
            tid = GeneratorUtils.generateUUID();
            MDC.put("tid", tid);
        }
        retrySubmission.setTid(tid);
        retrySubmission.setUid(GeneratorUtils.generateUUID());

        //method info
        retrySubmission.setBeanName(
                CsConstants.CLASS_NAME.equals(retry.bean()) ? joinPoint.getTarget().getClass().getName() : retry.bean());
        retrySubmission.setMethodName(signature.getName());

        //parameterTypes
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] classNames = new String[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            classNames[i] = parameterTypes[i].getName();
        }
        retrySubmission.setArgClassNames(classNames);
        retrySubmission.setArgStr(JsonSerializeUtil.toJSONStringGeneric(args));

        //retry info
        retrySubmission.setMaxRetryCnt(retry.maxRetry());

        //ext
        Map<String, Object> objectNode = RetryContext.getExt();
        // add error message  to ext
        objectNode.put("errMsg", ExceptionUtils.getStackTraceSMS(e));
        retrySubmission.setExt(objectNode.toString());

        return retrySubmission;
    }

}
