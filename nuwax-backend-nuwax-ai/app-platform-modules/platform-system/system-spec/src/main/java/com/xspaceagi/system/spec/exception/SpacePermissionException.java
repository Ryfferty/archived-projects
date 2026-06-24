package com.xspaceagi.system.spec.exception;

import com.xspaceagi.system.spec.enums.ErrorCodeEnum;
import lombok.Getter;

public class SpacePermissionException extends RuntimeException {

    @Getter
    private String code = ErrorCodeEnum.PERMISSION_DENIED.getCode();

    public SpacePermissionException() {
        super("您没有此空间数据的访问权限!");
    }

    public SpacePermissionException(String message) {
        super(message);
    }

    public SpacePermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpacePermissionException(Throwable cause) {
        super(cause);
    }
}