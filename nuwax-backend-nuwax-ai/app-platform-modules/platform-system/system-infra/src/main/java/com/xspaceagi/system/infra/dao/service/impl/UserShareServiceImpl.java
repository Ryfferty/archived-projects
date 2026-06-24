package com.xspaceagi.system.infra.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xspaceagi.system.infra.dao.entity.UserShare;
import com.xspaceagi.system.infra.dao.mapper.UserShareMapper;
import com.xspaceagi.system.infra.dao.service.UserShareService;
import com.xspaceagi.system.sdk.service.UserShareApiService;
import com.xspaceagi.system.sdk.service.dto.UserShareDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.UUID;

@Service
public class UserShareServiceImpl extends ServiceImpl<UserShareMapper, UserShare> implements UserShareService, UserShareApiService {

    @Override
    public UserShareDto addOrUpdateUserShare(UserShareDto userShareDto) {
        Assert.notNull(userShareDto, "userShareDto must be non-null");
        Assert.notNull(userShareDto.getUserId(), "userId must be non-null");
        Assert.notNull(userShareDto.getType(), "type must be non-null");
        UserShare userShare = new UserShare();
        BeanUtils.copyProperties(userShareDto, userShare);
        userShare.setShareKey(UUID.randomUUID().toString().replace("-", ""));
        this.saveOrUpdate(userShare);
        userShare = getById(userShare.getId());
        return convertToDto(userShare);
    }

    private UserShareDto convertToDto(UserShare userShare) {
        UserShareDto userShareDto = new UserShareDto();
        BeanUtils.copyProperties(userShare, userShareDto);
        return userShareDto;
    }

    @Override
    public UserShareDto getUserShare(String shareKey, boolean expiredNotReturn) {
        if (shareKey == null) {
            return null;
        }
        LambdaQueryWrapper<UserShare> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserShare::getShareKey, shareKey);
        return queryUserShare(queryWrapper, expiredNotReturn);
    }

    @Override
    public UserShareDto getUserShare(UserShareDto.UserShareType type, String targetId, boolean expiredNotReturn) {
        LambdaQueryWrapper<UserShare> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserShare::getType, type);
        queryWrapper.eq(UserShare::getTargetId, targetId);
        return queryUserShare(queryWrapper, expiredNotReturn);
    }

    private UserShareDto queryUserShare(LambdaQueryWrapper<UserShare> queryWrapper, boolean expiredNotReturn) {
        UserShare userShare = this.getOne(queryWrapper, false);
        if (userShare == null) {
            return null;
        }
        if (expiredNotReturn && userShare.getExpire() != null && userShare.getExpire().before(new Date())) {
            return null;
        }
        return convertToDto(userShare);
    }
}
