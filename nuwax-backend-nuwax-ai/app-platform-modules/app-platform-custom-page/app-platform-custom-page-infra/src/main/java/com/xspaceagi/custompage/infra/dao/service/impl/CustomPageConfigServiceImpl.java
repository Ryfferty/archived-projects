package com.xspaceagi.custompage.infra.dao.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.custompage.infra.dao.entity.CustomPageConfig;
import com.xspaceagi.custompage.infra.dao.mapper.CustomPageConfigMapper;
import com.xspaceagi.custompage.infra.dao.service.ICustomPageConfigService;

@Service
public class CustomPageConfigServiceImpl extends ServiceImpl<CustomPageConfigMapper, CustomPageConfig>
        implements ICustomPageConfigService {
}
