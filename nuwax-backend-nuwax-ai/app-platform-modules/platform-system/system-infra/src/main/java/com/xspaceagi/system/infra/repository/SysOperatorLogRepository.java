package com.xspaceagi.system.infra.repository;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.xspaceagi.system.infra.dao.mapper.SysOperatorLogMapper;
import com.xspaceagi.system.infra.dao.model.OperatorLogModel;
import com.xspaceagi.system.infra.dao.service.ISysOperatorLogService;
import com.xspaceagi.system.infra.translator.ISysOperatorLogTranslator;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class SysOperatorLogRepository implements ISysOperatorLogRepository {

    @Resource
    private ISysOperatorLogService sysOperatorLogService;

    @Resource
    private SysOperatorLogMapper sysOperatorLogMapper;

    @Resource
    private ISysOperatorLogTranslator sysOperatorLogTranslator;

    @Override
    public List<OperatorLogModel> pageQuery(Map<String, Object> queryMap, List<OrderItem> orderColumns, Long startIndex, Long pageSize) {
        var dataList = this.sysOperatorLogMapper.queryList(queryMap,
                orderColumns, startIndex, pageSize);
        var ans = dataList.stream()
                .map(sysUser -> this.sysOperatorLogTranslator.convertToModel(sysUser))
                .collect(Collectors.toList());


        return ans;
    }

    @Override
    public Long queryTotal(Map<String, Object> queryMap) {
        return this.sysOperatorLogMapper.queryTotal(queryMap);

    }

    @Override
    public OperatorLogModel queryOneInfoById(Long id) {
        var entity = this.sysOperatorLogService.queryOneById(id);
        var model = this.sysOperatorLogTranslator.convertToModel(entity);
        return model;

    }

    @Override
    public Long addInfo(OperatorLogModel model) {
        var entity = this.sysOperatorLogTranslator.convertToEntity(model);
        var id = this.sysOperatorLogService.addInfo(entity);
        return id;
    }
}
