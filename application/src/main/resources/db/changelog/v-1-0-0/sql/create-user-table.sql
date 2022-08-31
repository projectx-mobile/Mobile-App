CREATE TABLE sec_users
(
    id              BIGINT UNSIGNED AUTO_INCREMENT primary key,
    email           VARCHAR(255) NOT NULL UNIQUE,
    name            VARCHAR(255) NOT NULL,
    points          BIGINT DEFAULT 0,
    user_role       VARCHAR(255) CHECK ( user_role in ('ADMIN', 'CHILD', 'PARENT')),
    user_status     VARCHAR(255) CHECK ( user_status in ('ACTIVE', 'BANNED', 'REMOVED')),
    family_id       VARCHAR(255)
);
