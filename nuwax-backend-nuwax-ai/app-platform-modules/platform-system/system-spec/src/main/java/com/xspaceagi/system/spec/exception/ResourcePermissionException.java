package com.xspaceagi.system.spec.exception;

/**
 * 资源权限异常
 */
public class ResourcePermissionException extends RuntimeException {
    
    public ResourcePermissionException(String message) {
        super(message);
    }
    
    public ResourcePermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}