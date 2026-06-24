package com.xspaceagi.system.sdk.retry.server;


import com.xspaceagi.system.sdk.retry.dto.RetrySubmission;
import com.xspaceagi.system.spec.dto.ReqResult;

public interface IRetrySubmitService {

    ReqResult<Void> submitRetryData(RetrySubmission retrySubmission);

}
