package com.xspaceagi.system.domain.facade.rpc;

import com.xspaceagi.system.infra.dao.model.OperatorLogModel;
import com.xspaceagi.system.infra.repository.ISysOperatorLogRepository;
import com.xspaceagi.system.sdk.retry.annotation.Retry;
import com.xspaceagi.system.sdk.server.ITrackerReportService;

import com.xspaceagi.system.spec.common.OperatorLogContext;
import com.xspaceagi.system.spec.common.RequestContext;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TrackerReportServiceImpl implements ITrackerReportService {

    @Resource
    private ISysOperatorLogRepository sysOperatorLogRepository;

    @Retry(async = false)
    @Override
    public void reportLog(OperatorLogContext context) {

        // RequestContext.get() 为空,租户id就为空,无法插入日志,忽略插入日志
        if (Objects.isNull(RequestContext.get()) || Objects.isNull(RequestContext.get().getTenantId())) {
            log.warn("RequestContext.get() 为空,租户id就为空,无法插入日志,忽略插入日志");
            return;
        }

        var model = OperatorLogModel.convertToModel(context);

        this.sysOperatorLogRepository.addInfo(model);
    }
}
