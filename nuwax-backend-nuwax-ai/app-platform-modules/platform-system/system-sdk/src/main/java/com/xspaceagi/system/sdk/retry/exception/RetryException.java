package com.xspaceagi.system.sdk.retry.exception;


public class RetryException extends RuntimeException {

    public RetryException() {
    }

    public RetryException(final String message) {
        super(message);
    }

    public RetryException(final Throwable cause) {
        super(cause);
    }
}
