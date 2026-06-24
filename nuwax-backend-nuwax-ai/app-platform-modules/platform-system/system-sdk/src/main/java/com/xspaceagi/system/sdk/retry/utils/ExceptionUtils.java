package com.xspaceagi.system.sdk.retry.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {

    private static final int showLength   = 100;
    private static final int smsLength    = 420;
    private static final int mediumLength = 1000;
    private static final int moreLength   = 2500;

    /**
     * 获取异常的堆栈信息
     *
     * @return
     */
    public static String getStackTrace(Throwable t) {
        if (t == null) {
            return null;
        }
        PrintWriter pw = null;
        try {
            StringWriter sw = new StringWriter();
            pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 截取异常的部分堆栈信息
     *
     * @return
     */
    public static String getStackTrace(Throwable t, int len) {
        String stackTrace = getStackTrace(t);
        return (stackTrace != null && stackTrace.length() > len) ? stackTrace.substring(0, len) + "..." : stackTrace;
    }

    /**
     * 截取异常的部分堆栈信息用于展示
     *
     * @return
     */
    public static String getStackTraceShow(Throwable t) {
        String stackTrace = getStackTrace(t);
        return (stackTrace != null && stackTrace.length() > showLength) ?
            stackTrace.substring(0, showLength) + "..." :
            stackTrace;
    }

    /**
     * 截取异常的部分堆栈信息用于发送短信告警
     *
     * @return
     */
    public static String getStackTraceSMS(Throwable t) {
        String stackTrace = getStackTrace(t);
        return (stackTrace != null && stackTrace.length() > smsLength) ?
            stackTrace.substring(0, smsLength) + "..." :
            stackTrace;
    }

    public static String getStackTraceMedium(Throwable t) {
        String stackTrace = getStackTrace(t);
        return (stackTrace != null && stackTrace.length() > mediumLength) ?
            stackTrace.substring(0, mediumLength) + "..." :
            stackTrace;
    }

    public static String getStackTraceMore(Throwable t) {
        String stackTrace = getStackTrace(t);
        return (stackTrace != null && stackTrace.length() > moreLength) ?
            stackTrace.substring(0, moreLength) + "..." :
            stackTrace;
    }
}
