create table family_tasks
(
    id          bigint                not null,
    is_daily    boolean default false not null,
    deadline    datetime(6),
    points      bigint,
    task_status varchar(255),
    task_id     bigint,
    family_id   varchar(255),
    primary key (id)
)