drop table if exists `address`;

create table `address`
(
    id      bigint      not null comment '主键id',
    user_id bigint      not null comment '用户id',
    address varchar(50) not null comment '地址',
    primary key (id)
);
