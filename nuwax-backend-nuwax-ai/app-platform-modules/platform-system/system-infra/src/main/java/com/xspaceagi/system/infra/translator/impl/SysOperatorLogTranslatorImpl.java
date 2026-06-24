package com.xspaceagi.system.infra.translator.impl;

import com.xspaceagi.system.infra.dao.entity.SysOperatorLog;
import com.xspaceagi.system.infra.dao.model.OperatorLogModel;
import com.xspaceagi.system.infra.translator.ISysOperatorLogTranslator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class SysOperatorLogTranslatorImpl implements ISysOperatorLogTranslator {
    @Override
    public OperatorLogModel convertToModel(SysOperatorLog entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        OperatorLogModel sysOperatorLogModel = new OperatorLogModel();
        sysOperatorLogModel.setId(entity.getId());
        sysOperatorLogModel.setTenantId(entity.getTenantId());
        sysOperatorLogModel.setOperateType(entity.getOperateType());
        sysOperatorLogModel.setSystemCode(entity.getSystemCode());
        sysOperatorLogModel.setSystemName(entity.getSystemName());
        sysOperatorLogModel.setObjectOp(entity.getObjectOp());
        sysOperatorLogModel.setAction(entity.getAction());
        sysOperatorLogModel.setOperateContent(entity.getOperateContent());
        sysOperatorLogModel.setExtraContent(entity.getExtraContent());
        sysOperatorLogModel.setOrgId(entity.getOrgId());
        sysOperatorLogModel.setOrgName(entity.getOrgName());
        sysOperatorLogModel.setCreatorId(entity.getCreatorId());
        sysOperatorLogModel.setCreator(entity.getCreator());
        sysOperatorLogModel.setCreated(entity.getCreated());
        sysOperatorLogModel.setModified(entity.getModified());
        return sysOperatorLogModel;

    }

    @Override
    public SysOperatorLog convertToEntity(OperatorLogModel model) {
        if (Objects.isNull(model)) {
            return null;
        }
        SysOperatorLog sysOperatorLog = new SysOperatorLog();
        sysOperatorLog.setId(model.getId());
        sysOperatorLog.setTenantId(model.getTenantId());
        sysOperatorLog.setOperateType(model.getOperateType());
        sysOperatorLog.setSystemCode(model.getSystemCode());
        sysOperatorLog.setSystemName(model.getSystemName());
        sysOperatorLog.setObjectOp(model.getObjectOp());
        sysOperatorLog.setAction(model.getAction());
        sysOperatorLog.setOperateContent(model.getOperateContent());
        sysOperatorLog.setExtraContent(model.getExtraContent());
        sysOperatorLog.setOrgId(model.getOrgId());
        sysOperatorLog.setOrgName(model.getOrgName());
        sysOperatorLog.setCreatorId(model.getCreatorId());
        sysOperatorLog.setCreator(model.getCreator());
        sysOperatorLog.setCreated(model.getCreated());
        sysOperatorLog.setModified(model.getModified());
        return sysOperatorLog;

    }
}
