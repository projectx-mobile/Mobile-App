create table category
(
    id            bigint not null auto_increment primary key,
    category_type varchar(255),
    description   varchar(255),
    title         varchar(255)
)