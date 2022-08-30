CREATE TABLE reward
(
    id               BIGINT UNSIGNED AUTO_INCREMENT primary key,
    title            VARCHAR(255) NOT NULL,
    points           BIGINT DEFAULT 0,
    reward_status    VARCHAR(255) CHECK ( reward_status in ('LOCKED', 'UNLOCKED')),
    photo            VARCHAR(255),
    family_id        VARCHAR(255) NOT NULL
);