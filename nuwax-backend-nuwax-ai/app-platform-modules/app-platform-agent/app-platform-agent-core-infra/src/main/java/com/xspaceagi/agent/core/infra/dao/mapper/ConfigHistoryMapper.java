package com.xspaceagi.agent.core.infra.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xspaceagi.agent.core.adapter.repository.entity.ConfigHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConfigHistoryMapper extends BaseMapper<ConfigHistory> {

}
