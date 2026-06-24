package com.xspaceagi.system.sdk.server;


import com.xspaceagi.system.spec.common.OperatorLogContext;

/**
 * 日志上报
 */
public interface ITrackerReportService {

    /**
     * 上报日志
     *
     * @param context 上下文
     */
    void reportLog(OperatorLogContext context);

}
