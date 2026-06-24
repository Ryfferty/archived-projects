package com.xspaceagi.system.domain.log;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogPrintConfiguration.class)
public @interface EnableLogPrint {


}
