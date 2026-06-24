package com.xspaceagi.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

/**
 * tid ,获取拦截器
 *
 * @author soddy
 */
@Slf4j
@Component
public class TIdInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Integer reqId = ThreadLocalRandom.current().nextInt(100, 999999);
        MDC.put("tid", reqId + "" + Instant.now().toEpochMilli());

        // 请求开始时间
        request.setAttribute("reqBeginTime", Instant.now().toEpochMilli());
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            request.setAttribute("methodName", method.getDeclaringClass().getName() + "." + method.getName());
        } else {
            request.setAttribute("methodName", "静态资源未识别的方法");
        }


        return true;
    }
}
