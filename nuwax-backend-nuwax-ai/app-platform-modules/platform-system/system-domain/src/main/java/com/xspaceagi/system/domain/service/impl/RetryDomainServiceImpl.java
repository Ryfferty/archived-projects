package com.xspaceagi.system.domain.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.xspaceagi.system.domain.service.RetryDomainService;
import com.xspaceagi.system.sdk.retry.dto.RetryExecDto;
import com.xspaceagi.system.sdk.retry.utils.ExceptionUtils;
import com.xspaceagi.system.infra.dao.entity.RetryData;
import com.xspaceagi.system.infra.dao.service.RetryDataService;
import com.xspaceagi.system.infra.rpc.RetryCallBackRpcService;
import com.xspaceagi.system.spec.dto.ReqResult;
import com.xspaceagi.system.spec.enums.RetryStatusEnum;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RetryDomainServiceImpl implements RetryDomainService {

    private static final int MAX_EXEC_CT = 1000;
    @Resource
    private RetryDataService retryDataService;

    @Resource
    private RetryCallBackRpcService retryCallBackRpcService;

    private ScheduledExecutorService retryExecutor = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init() {
        retryExecutor.scheduleAtFixedRate(() -> {
            try {
                int ct = MAX_EXEC_CT;
                RetryData retryData = queryWaitingRetryDataAndLock();
                while (null != retryData && ct-- > 0) {
                    exec(retryData, true);
                    retryData = queryWaitingRetryDataAndLock();
                }
            } catch (Exception e) {
                log.error("重试数据处理错误", e);
            }
        }, 50, 5, TimeUnit.SECONDS);
    }

    private RetryData queryWaitingRetryDataAndLock() {
        QueryWrapper<RetryData> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("status", Lists.newArrayList(RetryStatusEnum.WAIT.getValue(), RetryStatusEnum.FAIL.getValue()));
        queryWrapper.lt("lock_time", new Date());
        queryWrapper.last("LIMIT 1");
        RetryData retryDataEntity = retryDataService.getOne(queryWrapper);
        if (retryDataEntity == null) {
            return null;
        }
        int retryCt = retryDataEntity.getRetryCnt() + 1;
        int step = retryCt > retryDataEntity.getMaxRetryCnt() ? retryDataEntity.getMaxRetryCnt() : retryCt;
        retryDataEntity.setLockTime(new Date(System.currentTimeMillis() + 30000 * step));//30秒内不能再重试
        retryDataService.updateById(retryDataEntity);
        return retryDataEntity;
    }

    /**
     * 执行重试任务
     */
    private void exec(RetryData retryData, boolean checkRetryTimes) {
        if (retryData == null) {
            return;
        }
        Integer reqId = ThreadLocalRandom.current().nextInt(100, 999999);
        MDC.put("tid", reqId + "" + Instant.now().toEpochMilli());
        try {
            RetryExecDto dto = new RetryExecDto();
            dto.setBeanName(retryData.getBeanName());
            dto.setArgStr(retryData.getArgStr());
            dto.setMethodName(retryData.getMethodName());
            dto.setArgClassNames(JSONObject.parseObject(retryData.getArgClassNames(), String[].class));
            ReqResult<?> result = retryCallBackRpcService.methodInvoke(dto);
            retryData.setStatus(RetryStatusEnum.SUCCESS.getValue());
            if (!result.isSuccess()) {
                retryData.setStatus(RetryStatusEnum.FAIL.getValue());
            }
            String resultStr = JSONObject.toJSONString(result);
            retryData.setResult(resultStr);
            retryData.setRetryCnt(retryData.getRetryCnt() + 1);
            if (checkRetryTimes && retryData.getRetryCnt() >= retryData.getMaxRetryCnt()) {
                retryData.setStatus(RetryStatusEnum.BAN.getValue());
            }
            log.info("重试结果, class: {}, method:{}, result: {}", retryData.getBeanName(), retryData.getMethodName(), resultStr);
        } catch (Throwable e) {
            log.error("重试异常, class: {}, method:{}", retryData.getBeanName(), retryData.getMethodName(), e);
            retryData.setResult(ExceptionUtils.getStackTrace(e));
            retryData.setStatus(RetryStatusEnum.FAIL.getValue());
        } finally {
            retryDataService.updateById(retryData);
            MDC.clear();
        }
    }
}
