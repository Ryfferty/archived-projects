package com.xspaceagi.system.infra.rpc;

import com.xspaceagi.system.sdk.retry.callback.IRetryCallBack;
import com.xspaceagi.system.sdk.retry.dto.RetryExecDto;
import com.xspaceagi.system.spec.dto.ReqResult;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class RetryCallBackRpcService {

    @Resource
    private IRetryCallBack iRetryCallBack;

    public ReqResult methodInvoke(RetryExecDto dto) {
        return iRetryCallBack.methodInvoke(dto);
    }
}
