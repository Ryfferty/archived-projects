package com.xspaceagi.system.infra.verify.sms;

import lombok.Data;

@Data
public class SmsConfig {
    private String smsAccessKeyId;
    private String smsAccessKeySecret;
    private String smsSignName;
    private String smsTemplateCode;
}
