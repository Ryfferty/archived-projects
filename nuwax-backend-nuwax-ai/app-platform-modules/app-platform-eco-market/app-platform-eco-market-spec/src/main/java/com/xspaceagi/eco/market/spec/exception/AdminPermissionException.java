package com.xspaceagi.eco.market.spec.exception;

/**
 * 管理员权限异常
 * 当用户没有管理员权限访问接口时抛出
 * 
 * @author soddy
 */
public class AdminPermissionException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    public AdminPermissionException() {
        super("当前用户没有管理员权限");
    }
    
    public AdminPermissionException(String message) {
        super(message);
    }
    
    public AdminPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public AdminPermissionException(Throwable cause) {
        super("当前用户没有管理员权限", cause);
    }
}