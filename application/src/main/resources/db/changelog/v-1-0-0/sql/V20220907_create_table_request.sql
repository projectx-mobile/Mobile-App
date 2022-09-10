create table request
(
    id             bigint not null auto_increment,
    request_status varchar(255),
    reward_id      bigint,
    primary key (id)
)

