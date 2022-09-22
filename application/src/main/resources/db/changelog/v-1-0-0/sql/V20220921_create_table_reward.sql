create table reward
(
    id            bigint not null auto_increment,
    points        bigint,
    reward_status varchar(255),
    reward_type   varchar(255),
    title         varchar(255),
    primary key (id)
)

