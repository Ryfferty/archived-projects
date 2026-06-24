create table eco_market_client_secret (
    id bigint auto_increment comment '主键id' primary key,
    name varchar(128) not null comment '名称',
    description varchar(256) null comment '描述',
    client_id varchar(128) not null comment '客户端ID,分布式唯一UUID',
    client_secret varchar(256) null comment '客户端密钥',
    _tenant_id bigint not null comment '租户ID',
    created datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator_id bigint null comment '创建人id',
    creator_name varchar(64) null comment '创建人',
    modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    yn tinyint default 1 null comment '逻辑标记,1:有效;-1:无效',
    constraint uk_client_id unique (client_id) comment '客户端ID唯一索引'
) comment '生态市场,客户端端配置';

create table eco_market_client_config (
    id bigint auto_increment comment '主键id' primary key,
    uid varchar(128) not null comment '唯一ID,分布式唯一UUID',
    name varchar(128) not null comment '名称',
    description varchar(256) null comment '描述',
    data_type tinyint default 1 not null comment '市场类型,默认插件,1:插件;2:模板;3:MCP',
    target_type varchar(64) null comment '细分类型,比如: 插件,智能体,工作流',
    target_id  bigint null comment '具体目标的id,可以智能体,工作流,插件,还有mcp等',
    category_code varchar(128) null comment '分类编码,商业服务等,通过接口获取',
    category_name varchar(128) null comment '分类名称,商业服务等,通过接口获取',
    owned_flag tinyint default 0 not null comment '是否我的分享,0:否(生态市场获取的);1:是(我的分享)',
    share_status tinyint default 1 not null comment '分享状态,1:草稿;2:审核中;3:已发布;4:已下线;5:驳回',
    use_status tinyint default 2 not null comment '使用状态,1:启用;2:禁用;',
    publish_time datetime null comment '发布时间',
    offline_time datetime null comment '下线时间',
    version_number bigint default 1 not null comment '版本号,自增,发布一次增加1,初始值为1',
    author varchar(256) null comment '作者信息',
    publish_doc mediumtext null comment '发布文档',
    config_param_json json null comment '请求参数配置json',
    config_json json null comment '配置json,存储插件的配置信息如果有其他额外的信息保存放这里',
    icon varchar(255) null comment '图标图片地址',
    _tenant_id bigint not null comment '租户ID',
    create_client_id varchar(128) not null comment '创建者的客户端ID',
    created datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    creator_id bigint null comment '创建人id',
    creator_name varchar(64) null comment '创建人',
    modified datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    modified_id bigint null comment '最后修改人id',
    modified_name varchar(64) null comment '最后修改人',
    yn tinyint default 1 null comment '逻辑标记,1:有效;-1:无效',
    constraint uk_uid unique (uid) comment 'uid唯一索引'
) comment '生态市场配置';
create table eco_market_client_publish_config like eco_market_client_config;
ALTER TABLE eco_market_client_publish_config COMMENT '生态市场,客户端,已发布配置';

create table eco_market_server_secret like eco_market_client_secret;
ALTER TABLE eco_market_server_secret COMMENT '生态市场,服务器端配置';

create table eco_market_server_config like eco_market_client_config ;
ALTER TABLE eco_market_server_config COMMENT '生态市场,服务器端配置';

create table eco_market_server_publish_config like eco_market_client_config ;
ALTER TABLE eco_market_server_publish_config COMMENT '生态市场,服务器端,已发布配置';
