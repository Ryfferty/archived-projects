package com.xspaceagi.system.sdk.retry.callback;

import com.xspaceagi.system.sdk.retry.dto.RetryExecDto;
import com.xspaceagi.system.spec.dto.ReqResult;

public interface IRetryCallBack {

    ReqResult<?> methodInvoke(RetryExecDto dto);

}
