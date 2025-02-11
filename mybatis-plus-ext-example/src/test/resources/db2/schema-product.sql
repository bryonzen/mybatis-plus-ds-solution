drop table if exists `product`;

create table `product`
(
    id   bigint      not null comment '主键id',
    name varchar(20) not null comment '名称',
    primary key (id)
);
