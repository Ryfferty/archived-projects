package com.xspaceagi.system.domain.log;

import com.xspaceagi.system.domain.log.record.LogPrintAspect;
import com.xspaceagi.system.domain.log.record.LogPrintAspectSupport;
import com.xspaceagi.system.domain.log.record.LogRecordPrintAspect;
import com.xspaceagi.system.domain.log.record.LogRecordPrintAspectSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogPrintConfiguration {


    /**
     * 打印请求入参和结果
     */
    @Bean
    public LogRecordPrintAspectSupport logRecordPrintAspectSupport() {
        return new LogRecordPrintAspectSupport();
    }

    @Bean
    public LogRecordPrintAspect logRecordPrintAspect() {
        return new LogRecordPrintAspect();
    }


    @Bean
    public LogPrintAspectSupport logPrintAspectSupport() {
        return new LogPrintAspectSupport();
    }

    @Bean
    public LogPrintAspect logPrintAspect() {
        return new LogPrintAspect();
    }

}
