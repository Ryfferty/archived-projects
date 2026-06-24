package com.xspaceagi.system.application.service;


import com.xspaceagi.system.application.dto.EventDto;
import com.xspaceagi.system.application.dto.NotifyMessageDto;
import com.xspaceagi.system.application.dto.NotifyMessageQueryDto;
import com.xspaceagi.system.application.dto.SendNotifyMessageDto;

import java.util.List;

public interface NotifyMessageApplicationService {

    Long sendNotifyMessage(SendNotifyMessageDto sendNotifyMessageDto);

    /**
     * 查询用户消息列表
     */
    List<NotifyMessageDto> queryNotifyMessageList(NotifyMessageQueryDto messageQueryDto);

    /**
     * 查询未读数量
     */
    Long countUnreadNotifyMessage(Long userId);

    /**
     * 更新已读状态
     */
    void updateReadStatus(Long userId, List<Long> notifyIds);

    /**
     * 全部更新为已读
     */
    void updateAllUnreadNotifyMessage(Long userId);

    void publishEvent(Long userId, EventDto<?> eventDto);

    List<EventDto<?>> collectEventList(Long userId, String clientId);

    void clearEventList(Long userId, String clientId);
}
