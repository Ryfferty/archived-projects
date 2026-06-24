package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.RetryData;
import com.xspaceagi.system.infra.dao.mapper.RetryDataMapper;
import com.xspaceagi.system.infra.dao.service.RetryDataService;
import org.springframework.stereotype.Service;

@Service
public class RetryDataServiceImpl extends ServiceImpl<RetryDataMapper, RetryData> implements RetryDataService {
}
