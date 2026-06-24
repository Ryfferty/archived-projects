package com.xspaceagi.system.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "认证token，认证方式，header中传Authorization:Bearer token")
    private String token;

    @Schema(description = "token过期时间")
    private Date expireDate;

    @Schema(description = "判断用户是否设置过密码，如果未设置过，需要弹出密码设置框让用户设置密码")
    private Integer resetPass;

    @Schema(description = "登录成功后跳转的页面")
    private String redirect;
}
