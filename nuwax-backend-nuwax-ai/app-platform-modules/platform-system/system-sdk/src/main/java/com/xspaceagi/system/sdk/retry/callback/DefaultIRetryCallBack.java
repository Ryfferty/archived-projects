package com.xspaceagi.system.sdk.retry.callback;

import com.alibaba.fastjson2.JSONObject;
import com.xspaceagi.system.sdk.retry.dto.RetryExecDto;
import com.xspaceagi.system.sdk.retry.utils.ExceptionUtils;
import com.xspaceagi.system.spec.dto.ReqResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultIRetryCallBack implements IRetryCallBack {

    @Autowired
    private RetryMethodInvoker retryMethodInvoker;

    @Override
    public ReqResult<?> methodInvoke(RetryExecDto dto) {
        log.info("重试回调开始,dto:{}", JSONObject.toJSONString(dto));

        try {
            Object result = retryMethodInvoker.methodInvoke(dto);
            log.info("重试回调成功,result:{}", JSONObject.toJSONString(result));
            return ReqResult.success(result);
        } catch (Throwable e) {
            log.error("重试回调异常", e);
            return ReqResult.error(ExceptionUtils.getStackTrace(e));
        }
    }

}
