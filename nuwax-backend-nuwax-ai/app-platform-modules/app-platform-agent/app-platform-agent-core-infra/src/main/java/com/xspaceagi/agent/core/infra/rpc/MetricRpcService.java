package com.xspaceagi.agent.core.infra.rpc;

import com.xspaceagi.system.sdk.server.IUserMetricRpcService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class MetricRpcService {

    @Resource
    private IUserMetricRpcService iUserMetricRpcService;

    public void incrementMetricAllPeriods(Long tenantId, Long userId, String bizType, BigDecimal delta) {
        try {
            iUserMetricRpcService.incrementMetricAllPeriods(tenantId, userId, bizType, delta);
        } catch (Exception e) {
            log.error("incrementMetricAllPeriods error", e);
        }
    }

    public BigDecimal queryMetricCurrent(Long tenantId, Long userId, String bizType, String periodType) {
        try {
            return iUserMetricRpcService.queryMetricCurrent(tenantId, userId, bizType, periodType);
        } catch (Exception e) {
            log.error("queryMetricCurrent error", e);
            // 异常时返回-1不限制
            return BigDecimal.valueOf(-1L);
        }
    }
}
