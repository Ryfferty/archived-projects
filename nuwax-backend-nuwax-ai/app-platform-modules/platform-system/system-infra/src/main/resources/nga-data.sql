CREATE DATABASE nga;
USE nga;

drop table user;

CREATE TABLE user (
                      user_id bigint(20) PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
                      username VARCHAR(255) NOT NULL DEFAULT 'DefaultUsername' COMMENT '用户名',
                      avatar VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '头像URL',
                      email VARCHAR(255) NOT NULL UNIQUE COMMENT '用户邮箱',
                      email_verify_code VARCHAR(255) NOT NULL UNIQUE COMMENT '用户邮箱验证码',
                      wallet_address VARCHAR(255) NOT NULL COMMENT '钱包地址',
                      register_date DATETIME NOT NULL DEFAULT current_timestamp() COMMENT '注册日期',
                      last_login_time DATETIME COMMENT '最后登录时间',
                      account_status ENUM('Normal', 'Disabled') NOT NULL DEFAULT 'Normal' COMMENT '账户状态',
                      `created` datetime NOT NULL DEFAULT current_timestamp() COMMENT '记录创建时间',
                      `modified` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '记录最后一次修改时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户信息表';

drop table `union`;

CREATE TABLE `union` (
                         union_id bigint(20) PRIMARY KEY AUTO_INCREMENT COMMENT '公会ID',
                         union_name VARCHAR(255) NOT NULL COMMENT '公会名称',
                         union_description TEXT NOT NULL COMMENT '公会描述',
                         union_tag TEXT NOT NULL COMMENT '公会标签，用于分类和搜索',
                         img_url VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '公会图片URL',
                         icon_url VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '公会图标URL',
                         user_count INT NOT NULL DEFAULT 0 COMMENT '加入工会的用户数量',
                         user_base_count INT NOT NULL DEFAULT 0 COMMENT '工会基础用户数量',
                         social_media json COMMENT '公会社交媒体信息',
                         union_status ENUM('Normal', 'Pending', 'Disabled') NOT NULL DEFAULT 'Pending' COMMENT '公会状态',
                         union_admin TEXT COMMENT '公会管理员列表，存储管理员信息',
                         `created` datetime NOT NULL DEFAULT current_timestamp() COMMENT '记录创建时间',
                         `modified` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '记录最后一次修改时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='公会信息表';

CREATE TABLE super_node (
                            node_name VARCHAR(255) NOT NULL COMMENT '节点名称',
                            node_id bigint(20) PRIMARY KEY AUTO_INCREMENT COMMENT '超级节点ID',
                            union_id bigint(20) NOT NULL COMMENT '公会ID',
                            union_name VARCHAR(255) NOT NULL COMMENT '公会名称',
                            node_ca VARCHAR(255) NOT NULL DEFAULT '' COMMENT '节点合约地址，用户发起质押时使用',
                            crowdfunding_start_date DATETIME NOT NULL COMMENT '众筹开始日期',
                            crowdfunding_end_date DATETIME NOT NULL COMMENT '众筹结束日期',
                            crowdfunding_hard_cap DECIMAL(20, 10) NOT NULL COMMENT '质押上限（硬顶）',
                            profit_distribution_cycle INT NOT NULL COMMENT '利润分配周期（天）',
                            manage_fee_ratio VARCHAR(255) NOT NULL COMMENT '管理费用率',
                            min_stake DECIMAL(20, 10) NOT NULL COMMENT '每用户最少质押量',
                            total_staked DECIMAL(20, 10) NOT NULL DEFAULT 0.0 COMMENT '当前总质押量',
                            staking_start_time DATETIME NOT NULL COMMENT '质押开始时间',
                            staking_end_time DATETIME NOT NULL COMMENT '质押结束时间',
                            stake_duration INT NOT NULL COMMENT '质押持续时间（天）',
                            application_status ENUM('Pending', 'Approved', 'Rejected') NOT NULL DEFAULT 'Pending' COMMENT '申请状态',
                            node_status ENUM('Applying', 'Published', 'Crowdfunding','Rejected', 'Staking', 'Completed','Failed') NOT NULL DEFAULT 'Applying' COMMENT '超级节点状态',
                            mark VARCHAR(255) DEFAULT '' COMMENT '备注，可以用于拒绝超级节点申请时的备注等',
                            `created` datetime NOT NULL DEFAULT current_timestamp() COMMENT '记录创建时间',
                            `modified` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '记录最后一次修改时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='超级节点信息表';

#
# drop table game_account;

CREATE TABLE game_account (
                              account_id bigint(20) PRIMARY KEY AUTO_INCREMENT COMMENT '账号ID',
                              broker_id bigint(20) NOT NULL COMMENT '经纪人ID',
                              union_id bigint(20) NOT NULL COMMENT '公会ID',
                              game_name VARCHAR(255) NOT NULL COMMENT '游戏名称',
                              account_detail TEXT NOT NULL COMMENT '账号详细信息',
                              stake_amount DECIMAL(20, 10) NOT NULL COMMENT '质押金额',
                              reward_ratio varchar(32) NOT NULL COMMENT '收益分配比例',
                              rental_period INT NOT NULL COMMENT '租赁期限（天）',
                              account_status ENUM('Applied', 'Rented', 'Expired', 'Available') NOT NULL DEFAULT 'Applied' COMMENT '账号状态',
                              game_level VARCHAR(255) DEFAULT NULL COMMENT '游戏等级',
                              manage_fee_ratio varchar(32) DEFAULT NULL COMMENT '管理费比例',
                              rent_user_id BIGINT(20) DEFAULT NULL COMMENT '租赁用户ID',
                              rental_contact_info VARCHAR(1024) NOT NULL COMMENT '租赁联系方式',
                              rent_start_time datetime DEFAULT NULL COMMENT '出租开始时间',
                              rent_end_time datetime DEFAULT NULL COMMENT '出租结束时间',
                              platform_approval_time datetime DEFAULT NULL COMMENT '平台审批时间',
                              `created` datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                              `modified` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
                              KEY `idx_broker_id` (broker_id) USING BTREE COMMENT '经纪人ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='游戏账号租赁表';

CREATE TABLE union_admin (
                             admin_id bigint(20) PRIMARY KEY AUTO_INCREMENT COMMENT '管理员ID',
                             union_id bigint(20) NOT NULL COMMENT '公会ID',
                             name VARCHAR(255) NOT NULL COMMENT '管理员名称',
                             status ENUM('Enabled', 'Disabled') NOT NULL DEFAULT 'Enabled' COMMENT '状态，启用或禁用',
                             role VARCHAR(255) NOT NULL COMMENT '角色',
                             password VARCHAR(255) NOT NULL COMMENT '管理员密码',
                             wallet_address VARCHAR(255) NOT NULL COMMENT '钱包地址',
                             email VARCHAR(255) NOT NULL UNIQUE COMMENT '管理员邮箱',
                             last_login_time DATETIME COMMENT '最后登录时间',
                             created datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                             modified datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='工会管理员信息表';

CREATE TABLE platform_admin (
                                admin_id bigint(20) PRIMARY KEY AUTO_INCREMENT COMMENT '管理员ID',
                                name VARCHAR(255) NOT NULL COMMENT '管理员名称',
                                status ENUM('Enabled', 'Disabled') NOT NULL DEFAULT 'Enabled' COMMENT '状态，启用或禁用',
                                role VARCHAR(255) NOT NULL COMMENT '角色',
                                password VARCHAR(255) NOT NULL COMMENT '管理员密码',
                                wallet_address VARCHAR(255) NOT NULL COMMENT '钱包地址',
                                email VARCHAR(255) NOT NULL UNIQUE COMMENT '管理员邮箱',
                                last_login_time DATETIME COMMENT '最后登录时间',
                                created datetime NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
                                modified datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='平台管理员信息表';

