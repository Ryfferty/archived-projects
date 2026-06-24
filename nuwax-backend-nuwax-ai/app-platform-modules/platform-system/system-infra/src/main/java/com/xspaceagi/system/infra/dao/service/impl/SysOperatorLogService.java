package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.spec.enums.YnEnum;
import com.xspaceagi.system.infra.dao.entity.SysOperatorLog;
import com.xspaceagi.system.infra.dao.mapper.SysOperatorLogMapper;
import com.xspaceagi.system.infra.dao.service.ISysOperatorLogService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 操作日志(SysOperatorLog)服务实现类
 *
 * @author p1
 * @since 2024-11-01 11:16:02
 */
@Service
public class SysOperatorLogService extends ServiceImpl<SysOperatorLogMapper, SysOperatorLog> implements ISysOperatorLogService {

    @Override
    public SysOperatorLog queryOneById(Long id) {
        LambdaQueryWrapper<SysOperatorLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysOperatorLog::getYn, YnEnum.Y.getKey())
                .eq(SysOperatorLog::getId, id)
        ;

        return this.getOne(queryWrapper);
    }

    @Override
    public Long addInfo(SysOperatorLog sysOperatorLog) {
        sysOperatorLog.setId(null);
        sysOperatorLog.setCreated(null);
        sysOperatorLog.setModified(null);

        if (Objects.isNull(sysOperatorLog.getOrgName())) {
            sysOperatorLog.setOrgName("");
        }


        try {
            this.save(sysOperatorLog);
        } catch (Exception e) {
            log.error("添加操作日志失败:", e);
            throw e;
        }


        return sysOperatorLog.getId();
    }
}

