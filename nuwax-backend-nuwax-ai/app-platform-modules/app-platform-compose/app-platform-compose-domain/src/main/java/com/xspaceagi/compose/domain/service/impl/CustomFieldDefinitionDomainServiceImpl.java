package com.xspaceagi.compose.domain.service.impl;

import com.xspaceagi.compose.domain.repository.ICustomFieldDefinitionRepository;
import com.xspaceagi.compose.domain.service.CustomFieldDefinitionDomainService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomFieldDefinitionDomainServiceImpl implements CustomFieldDefinitionDomainService {



    @Resource
    private ICustomFieldDefinitionRepository customFieldDefinitionRepository;
}
