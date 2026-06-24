package com.xspaceagi.custompage.infra.dao.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.custompage.infra.dao.entity.CustomPageDomain;
import com.xspaceagi.custompage.infra.dao.mapper.CustomPageDomainMapper;
import com.xspaceagi.custompage.infra.dao.service.ICustomPageDomainService;

@Service
public class CustomPageDomainServiceImpl extends ServiceImpl<CustomPageDomainMapper, CustomPageDomain>
        implements ICustomPageDomainService {
}
