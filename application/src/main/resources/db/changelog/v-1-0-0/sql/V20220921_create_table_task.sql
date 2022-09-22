create table task
(
    id              bigint not null auto_increment,
    description     varchar(255),
    task_type       varchar(255),
    title           varchar(255),
    category_id     bigint,
    primary key (id)
)
