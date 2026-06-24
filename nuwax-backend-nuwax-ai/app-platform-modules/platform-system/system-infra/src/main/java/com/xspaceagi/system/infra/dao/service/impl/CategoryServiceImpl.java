package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.Category;
import com.xspaceagi.system.infra.dao.mapper.CategoryMapper;
import com.xspaceagi.system.infra.dao.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * 分类服务实现
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
