package com.xspaceagi.system.domain.log.record;

import com.xspaceagi.system.domain.log.LogRecordPrint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author soddy
 */
@Aspect
public class LogRecordPrintAspect {


    @Autowired
    private LogRecordPrintAspectSupport logRecordPrintAspectSupport;


    @Around("@annotation(logRecordPrint)")
    public Object around(ProceedingJoinPoint joinPoint, LogRecordPrint logRecordPrint) throws Throwable {
        return logRecordPrintAspectSupport.printLog(joinPoint, logRecordPrint);
    }


}
