package com.xspaceagi.system.spec.jackson;

public class JacksonProperties {
    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private String dateFormat = "yyyy-MM-dd";
    private String timeFormat = "HH:mm:ss";
    private String utilDateFormat;

    public JacksonProperties() {
    }

    public String getDateTimeFormat() {
        return this.dateTimeFormat;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public String getDateFormat() {
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTimeFormat() {
        return this.timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getUtilDateFormat() {
        return this.utilDateFormat;
    }

    public void setUtilDateFormat(String utilDateFormat) {
        this.utilDateFormat = utilDateFormat;
    }
}
