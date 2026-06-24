package com.xspaceagi.custompage.infra.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.custompage.infra.dao.entity.CustomPageConversation;

@Mapper
public interface CustomPageConversationMapper extends BaseMapper<CustomPageConversation> {
}
