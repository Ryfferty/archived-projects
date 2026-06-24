package com.xspaceagi.system.spec.exception;


import lombok.Getter;

/**
 * 通用抽象
 */
public abstract class KindlyException extends RuntimeException {

    @Getter
    private final String code;

    private String displayCode;


    public KindlyException(String code, String message) {
        super(message);
        this.code = code;
    }

    public KindlyException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }


    public String getDisplayCode() {
        if (null == getModule()) {
            return this.code;
        }

        return getModule() + this.code;
    }

    /**
     * 模块名称简拼
     *
     * @return
     */
    public abstract String getModule();

}
