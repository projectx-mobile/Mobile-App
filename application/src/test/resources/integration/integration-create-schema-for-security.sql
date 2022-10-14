SET DATABASE SQL SYNTAX PGS TRUE;

create table if not exists sec_user
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

create table if not exists client_app
(
    id          bigint not null auto_increment,
    app_id      varchar(255),
    updated     datetime(6),
    primary key (id)
)

create table if not exists sec_users_client_apps
(
    user_id         bigint,
    client_apps_id  bigint,

    constraint sec_users_client_apps_user_id_fkey
            foreign key (user_id) references sec_users
                on update cascade on delete cascade,
    constraint sec_users_client_apps_client_app_id_fkey
                foreign key (client_apps_id) references client_app
                    on update cascade on delete cascade
)



