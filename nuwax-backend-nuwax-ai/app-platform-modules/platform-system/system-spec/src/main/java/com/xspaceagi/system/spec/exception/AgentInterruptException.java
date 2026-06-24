package com.xspaceagi.system.spec.exception;

/**
 * agent执行中断
 */
public class AgentInterruptException extends RuntimeException {

    public AgentInterruptException(String message) {
        super(message);
    }

    public AgentInterruptException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgentInterruptException(Throwable cause) {
        super(cause);
    }

    public AgentInterruptException() {
        super();
    }
}
