package com.xspaceagi.system.domain.log.record;

import com.xspaceagi.system.domain.log.LogPrint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author soddy
 */
@Aspect
public class LogPrintAspect {


    @Autowired
    private LogPrintAspectSupport logPrintAspectSupport;


    /**
     * 方法环绕日志
     *
     * @param joinPoint
     * @param logPrint
     */
    @Around("@annotation(logPrint)")
    public Object around(ProceedingJoinPoint joinPoint, LogPrint logPrint) throws Throwable {
        return logPrintAspectSupport.printLog(joinPoint, logPrint);
    }


}
