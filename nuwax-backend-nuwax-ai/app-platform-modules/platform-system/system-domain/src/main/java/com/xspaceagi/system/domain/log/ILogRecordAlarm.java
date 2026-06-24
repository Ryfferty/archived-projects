package com.xspaceagi.system.domain.log;

/**
 * 告警接口,需要用户手动实现,如果开启 @LogRecordPrint 的sendAlarm 为 true的话
 */
public interface ILogRecordAlarm {

    /**
     * 发送告警信息
     *
     * @param alarmCode 告警自定义编码[可选值]
     * @param content   步骤,自定义
     * @param message   消息,自定义
     */
    void sendAlarm(String alarmCode, String content, String message);

}
