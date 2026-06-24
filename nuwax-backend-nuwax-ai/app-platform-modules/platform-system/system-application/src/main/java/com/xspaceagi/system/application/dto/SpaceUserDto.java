package com.xspaceagi.system.application.dto;

import com.xspaceagi.system.infra.dao.entity.SpaceUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SpaceUserDto implements Serializable {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户姓名")
    private String userName;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "空间ID")
    private Long spaceId;

    @Schema(description = "角色")
    private SpaceUser.Role role;

    @Schema(description = "更新时间")
    private Date modified;

    @Schema(description = "创建时间")
    private Date created;

    public String getUserName() {
        return userName == null ? "" : userName;
    }

    public String getNickName() {
        return nickName == null ? "" : nickName;
    }
}
