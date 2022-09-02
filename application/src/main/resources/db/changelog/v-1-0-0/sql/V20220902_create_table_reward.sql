CREATE TABLE reward
(
    id               BIGINT AUTO_INCREMENT primary key,
    title            VARCHAR(255) NOT NULL,
    points           BIGINT DEFAULT 0,
    reward_status    VARCHAR(255) CHECK ( reward_status in ('LOCKED', 'UNLOCKED')),
    reward_type      VARCHAR(255) CHECK ( reward_type in ('DEFAULT', 'CUSTOM')),
    photo            VARCHAR(255),
    family_id        VARCHAR(255) NOT NULL
);

