create table social_credentials
(
    id        bigint not null auto_increment,
    social_id bigint,
    sub       bigint,
    user_id   bigint,
    primary key (id)
)