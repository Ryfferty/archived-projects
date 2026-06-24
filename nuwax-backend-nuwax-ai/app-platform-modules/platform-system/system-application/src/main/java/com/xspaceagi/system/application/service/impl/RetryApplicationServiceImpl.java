package com.xspaceagi.system.application.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.xspaceagi.system.application.service.RetryApplicationService;
import com.xspaceagi.system.infra.dao.entity.RetryData;
import com.xspaceagi.system.infra.dao.service.RetryDataService;
import com.xspaceagi.system.sdk.retry.dto.RetrySubmission;
import com.xspaceagi.system.spec.enums.RetryStatusEnum;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Date;

@Service
public class RetryApplicationServiceImpl implements RetryApplicationService {

    @Resource
    private RetryDataService retryDataService;

    @Override
    public void submitRetryData(RetrySubmission retrySubmission) {
        RetryData retryDataEntity = new RetryData();
        retryDataEntity.setProjectCode(retrySubmission.getProjectCode());
        retryDataEntity.setAppCode(retrySubmission.getAppCode());
        retryDataEntity.setMaxRetryCnt(retrySubmission.getMaxRetryCnt());
        retryDataEntity.setExt(retrySubmission.getExt() == null ? "" : retrySubmission.getExt());
        retryDataEntity.setArgStr(retrySubmission.getArgStr());
        retryDataEntity.setBeanName(retrySubmission.getBeanName());
        retryDataEntity.setArgClassNames(JSONObject.toJSONString(retrySubmission.getArgClassNames()));
        retryDataEntity.setMethodName(retrySubmission.getMethodName());
        retryDataEntity.setTid(retrySubmission.getTid());
        retryDataEntity.setStatus(RetryStatusEnum.WAIT.getValue());
        retryDataEntity.setLockTime(new Date());
        retryDataService.save(retryDataEntity);
    }
}
