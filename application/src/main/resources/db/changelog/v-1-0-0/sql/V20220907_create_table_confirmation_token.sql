create table confirmation_token
(
    id              bigint not null auto_increment,
    confirmed_at    datetime(6),
    create_at       datetime(6),
    expires_at      datetime(6),
    token           varchar(255),
    user_id         bigint,
    primary key (id)
)