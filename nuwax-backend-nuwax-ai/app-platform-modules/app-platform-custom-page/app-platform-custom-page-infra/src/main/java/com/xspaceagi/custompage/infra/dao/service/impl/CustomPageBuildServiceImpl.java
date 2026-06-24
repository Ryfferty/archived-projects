package com.xspaceagi.custompage.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.custompage.infra.dao.entity.CustomPageBuild;
import com.xspaceagi.custompage.infra.dao.mapper.CustomPageBuildMapper;
import com.xspaceagi.custompage.infra.dao.service.ICustomPageBuildService;
import org.springframework.stereotype.Service;

@Service
public class CustomPageBuildServiceImpl extends ServiceImpl<CustomPageBuildMapper, CustomPageBuild>
        implements ICustomPageBuildService {
}


