package com.xspaceagi.system.domain.log;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

public class LogPrintEvaluationContext extends MethodBasedEvaluationContext {

    public LogPrintEvaluationContext(final Object rootObject, final Method method, final Object[] arguments,
                                     final ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }
}
