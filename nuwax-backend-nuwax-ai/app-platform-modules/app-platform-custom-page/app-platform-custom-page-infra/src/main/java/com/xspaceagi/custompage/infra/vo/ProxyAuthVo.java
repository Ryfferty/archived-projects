package com.xspaceagi.custompage.infra.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProxyAuthVo {

    private Tenant tenant;
    private User user;
    private Agent agent;
    private Space space;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private Long userId;
        private String uid;
        private String userName;
        private String nickName;
        private String avatar;
        private Role role;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Agent {
        private Long agentId;
        private String name;
        private String icon;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Space {
        private Long spaceId;
        private String name;
        private String icon;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tenant {
        private Long tenantId;
        private String name;
        private String icon;
    }

    public enum Role {
        SpaceAdmin,
        AgentCreator,
        SpaceUser,
        User
    }
}
