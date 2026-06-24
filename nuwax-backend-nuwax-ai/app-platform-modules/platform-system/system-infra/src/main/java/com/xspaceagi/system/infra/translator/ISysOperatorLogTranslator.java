package com.xspaceagi.system.infra.translator;

import com.xspaceagi.system.infra.dao.entity.SysOperatorLog;
import com.xspaceagi.system.infra.dao.model.OperatorLogModel;

/**
 * 操作日志数据转换
 */
public interface ISysOperatorLogTranslator {


    public OperatorLogModel convertToModel(SysOperatorLog entity);

    public SysOperatorLog convertToEntity(OperatorLogModel model);

}
