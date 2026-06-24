package com.xspaceagi.system.infra.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xspaceagi.system.infra.dao.entity.SysOperatorLog;

/**
 * 操作日志(SysOperatorLog)服务接口
 *
 * @author p1
 * @since 2024-11-01 11:16:02
 */
public interface ISysOperatorLogService extends IService<SysOperatorLog> {
    /**
     * 根据ID查询
     *
     * @param id id
     * @return 用户信息
     */
    SysOperatorLog queryOneById(Long id);


    /**
     * 添加
     */
    Long addInfo(SysOperatorLog sysOperatorLog);

}

