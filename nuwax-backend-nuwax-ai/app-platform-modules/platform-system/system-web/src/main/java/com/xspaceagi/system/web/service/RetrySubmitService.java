package com.xspaceagi.system.web.service;

import com.xspaceagi.system.sdk.retry.dto.RetrySubmission;
import com.xspaceagi.system.sdk.retry.server.IRetrySubmitService;
import com.xspaceagi.system.spec.dto.ReqResult;
import com.xspaceagi.system.application.service.RetryApplicationService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class RetrySubmitService implements IRetrySubmitService {

    @Resource
    private RetryApplicationService retryApplicationService;

    @Override
    public ReqResult<Void> submitRetryData(RetrySubmission retrySubmission) {
        retryApplicationService.submitRetryData(retrySubmission);
        return ReqResult.success();
    }
}
