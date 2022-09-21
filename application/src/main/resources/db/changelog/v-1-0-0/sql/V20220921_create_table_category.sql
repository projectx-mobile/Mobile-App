create table category
(
    id            bigint not null auto_increment primary key,
    title         varchar(255),
    description   varchar(255),
    category_type varchar(255)
)