CREATE TABLE task
(
    id              BIGINT UNSIGNED AUTO_INCREMENT primary key,
    title           VARCHAR(255) NOT NULL,
    description     VARCHAR(255),
    points          BIGINT DEFAULT 0,
    deadline        DATETIME NOT NULL,
    category        VARCHAR(255),
    task_status     VARCHAR(255) CHECK (task_status in ('ACTIVE', 'COMPLETE', 'OVERDUE', 'NOT_ACTIVE')),
    daily           BOOLEAN DEFAULT false,
    family_id       VARCHAR(255) NOT NULL
);