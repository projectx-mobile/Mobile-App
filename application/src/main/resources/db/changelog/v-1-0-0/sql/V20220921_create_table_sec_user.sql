create table sec_user
(
    id          bigint not null auto_increment,
    email       varchar(255),
    name        varchar(255),
    points      bigint,
    user_role   varchar(255),
    user_status varchar(255),
    family_id   varchar(255),
    primary key (id)
)
