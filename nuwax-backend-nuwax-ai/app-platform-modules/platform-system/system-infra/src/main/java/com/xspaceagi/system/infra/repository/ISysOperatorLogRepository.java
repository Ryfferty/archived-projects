package com.xspaceagi.system.infra.repository;

import com.xspaceagi.system.infra.dao.model.OperatorLogModel;
import com.xspaceagi.system.infra.service.IQueryViewService;

public interface ISysOperatorLogRepository extends IQueryViewService<OperatorLogModel> {


    /**
     * 根据id查询操作日志
     */
    OperatorLogModel queryOneInfoById(Long id);


    /**
     * 添加
     */
    Long addInfo(OperatorLogModel model);
}
