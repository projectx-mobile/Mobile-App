create table reward_request
(
    id             bigint not null auto_increment,
    request_status varchar(255),
    user_id        bigint,
    reward_id      bigint,
    primary key (id)
)

