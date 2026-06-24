package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.UserMetric;
import com.xspaceagi.system.infra.dao.mapper.UserMetricMapper;
import com.xspaceagi.system.infra.dao.service.UserMetricService;
import org.springframework.stereotype.Service;

@Service
public class UserMetricServiceImpl extends ServiceImpl<UserMetricMapper, UserMetric> implements UserMetricService {
}