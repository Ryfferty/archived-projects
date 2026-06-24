package com.xspaceagi.system.api;

import com.xspaceagi.system.domain.service.UserMetricDomainService;
import com.xspaceagi.system.infra.dao.entity.UserMetric;
import com.xspaceagi.system.sdk.server.IUserMetricRpcService;
import com.xspaceagi.system.sdk.service.dto.PeriodType;
import com.xspaceagi.system.sdk.service.dto.PeriodUtils;
import com.xspaceagi.system.sdk.service.dto.UserMetricDto;
import com.xspaceagi.system.spec.common.RequestContext;
import com.xspaceagi.system.spec.enums.PeriodTypeEnum;
import com.xspaceagi.system.spec.tenant.thread.TenantFunctions;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 用户计量服务 RPC 接口实现
 */
@Slf4j
@Service
public class UserMetricRpcServiceImpl implements IUserMetricRpcService {

    @Resource
    private UserMetricDomainService userMetricDomainService;

    public void incrementMetric(Long userId, String bizType, String periodType, String period, BigDecimal delta) {
        PeriodTypeEnum periodTypeEnum = PeriodTypeEnum.fromCode(periodType);
        if (periodTypeEnum == null) {
            throw new IllegalArgumentException("Invalid period type: " + periodType);
        }
        userMetricDomainService.incrementValue(userId, bizType, periodTypeEnum, period, delta);
    }

    public void incrementMetricCurrent(Long userId, String bizType, String periodType, BigDecimal delta) {
        String period = PeriodUtils.getCurrentPeriod(periodType);
        incrementMetric(userId, bizType, periodType, period, delta);
    }


    @Override
    @Transactional
    public void incrementMetricAllPeriods(Long tenantId, Long userId, String bizType, BigDecimal delta) {
        boolean hasContext = RequestContext.get() != null;
        if (!hasContext) {
            RequestContext.set(RequestContext.builder()
                    .tenantId(tenantId)
                    .build());
        }
        try {
            incrementMetricCurrent(userId, bizType, PeriodType.YEAR, delta);
            incrementMetricCurrent(userId, bizType, PeriodType.MONTH, delta);
            incrementMetricCurrent(userId, bizType, PeriodType.DAY, delta);
            incrementMetricCurrent(userId, bizType, PeriodType.HOUR, delta);
        } finally {
            if (!hasContext) {
                RequestContext.remove();
            }
        }
    }

    @Override
    public BigDecimal queryMetricCurrent(Long tenantId, Long userId, String bizType, String periodType) {
        PeriodTypeEnum periodTypeEnum = PeriodTypeEnum.fromCode(periodType);
        if (periodTypeEnum == null) {
            throw new IllegalArgumentException("Invalid period type: " + periodType);
        }
        String period = PeriodUtils.getCurrentPeriod(periodType);
        UserMetric userMetric = TenantFunctions.callWithIgnoreCheck(() -> userMetricDomainService.queryByUniqueKey(tenantId, userId, bizType, periodTypeEnum, period));
        return userMetric != null ? userMetric.getValue() : BigDecimal.ZERO;
    }

    private UserMetricDto convert(UserMetric userMetric) {
        if (userMetric == null) {
            return null;
        }
        UserMetricDto dto = new UserMetricDto();
        BeanUtils.copyProperties(userMetric, dto);
        return dto;
    }
}
