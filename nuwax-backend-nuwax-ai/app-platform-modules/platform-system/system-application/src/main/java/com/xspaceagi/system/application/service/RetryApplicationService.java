package com.xspaceagi.system.application.service;

import com.xspaceagi.system.sdk.retry.dto.RetrySubmission;

public interface RetryApplicationService {

    void submitRetryData(RetrySubmission retrySubmission);
}
