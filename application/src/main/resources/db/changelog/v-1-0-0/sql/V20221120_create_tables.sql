CREATE TABLE sec_user
(
    id                      BIGINT AUTO_INCREMENT NOT NULL,
    firebase_id             VARCHAR(255)          NULL,
    email                   VARCHAR(255)          NULL,
    points                  BIGINT                NULL,
    name                    VARCHAR(255)          NULL,
    family_id               VARCHAR(255)          NULL,
    user_role               VARCHAR(255)          NULL,
    user_status             VARCHAR(255)          NULL,
    parent_notifications_id BIGINT                NULL,
    child_notifications_id  BIGINT                NULL,
    CONSTRAINT pk_sec_user PRIMARY KEY (id)
);

CREATE TABLE user_photo
(
    user_id BIGINT NOT NULL,
    creation_date DATETIME(6),
    path VARCHAR(255)
);

CREATE TABLE family
(
    id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_family PRIMARY KEY (id)
);

CREATE TABLE family_task_user
(
    family_task_id BIGINT NOT NULL,
    user_id        BIGINT NOT NULL
);

CREATE TABLE family_task
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    task_id        BIGINT       NULL,
    creation       datetime     NULL,
    deadline       datetime     NULL,
    reward_points  BIGINT       NULL,
    penalty_points BIGINT       NULL,
    photo_report   BIT(1)       NOT NULL,
    task_status    VARCHAR(255) NULL,
    repeatable     BIT(1)       NOT NULL,
    user_id        BIGINT       NULL,
    family_id      VARCHAR(255) NULL,
    CONSTRAINT pk_family_task PRIMARY KEY (id)
);

CREATE TABLE task
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    category_id   BIGINT                NULL,
    task_type     VARCHAR(255)          NULL,
    CONSTRAINT pk_task PRIMARY KEY (id)
);

CREATE TABLE family_task_photo
(
    family_task_id BIGINT NOT NULL,
    creation_date DATETIME(6),
    path VARCHAR(255)
);

CREATE TABLE category
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    category_type VARCHAR(255)          NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE family_rewards_requests
(
    family_id  VARCHAR(255) NOT NULL,
    request_id BIGINT       NOT NULL
);

CREATE TABLE family_rewards
(
    family_id VARCHAR(255) NOT NULL,
    reward_id BIGINT       NOT NULL
);

CREATE TABLE reward
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255)          NULL,
    points        BIGINT                NULL,
    reward_status VARCHAR(255)          NULL,
    reward_type   VARCHAR(255)          NULL,
    CONSTRAINT pk_reward PRIMARY KEY (id)
);

CREATE TABLE reward_photo
(
    reward_id BIGINT NOT NULL,
    creation_date DATETIME(6),
    path VARCHAR(255)
);

CREATE TABLE reward_request
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    user_id        BIGINT                NULL,
    reward_id      BIGINT                NULL,
    request_status VARCHAR(255)          NULL,
    CONSTRAINT pk_reward_request PRIMARY KEY (id)
);

CREATE TABLE reward_request_photo
(
    reward_request_id BIGINT NOT NULL,
    creation_date DATETIME(6),
    path VARCHAR(255)
);

CREATE TABLE client_app
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    app_id  VARCHAR(255)          NULL,
    updated datetime              NULL,
    user_id BIGINT                NULL,
    CONSTRAINT pk_clientapp PRIMARY KEY (id)
);

CREATE TABLE child_notification
(
    id                  BIGINT AUTO_INCREMENT NOT NULL,
    all_off             BIT(1)                NOT NULL,
    new_task            BIT(1)                NOT NULL,
    confirm_task        BIT(1)                NOT NULL,
    confirm_reward      BIT(1)                NOT NULL,
    penalty_and_bonus   BIT(1)                NOT NULL,
    task_reminder       BIT(1)                NOT NULL,
    notification_period VARCHAR(255)          NULL,
    CONSTRAINT pk_child_notification PRIMARY KEY (id)
);

CREATE TABLE parent_notification
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    all_off         BIT(1)                NOT NULL,
    new_task_status BIT(1)                NOT NULL,
    new_request     BIT(1)                NOT NULL,
    new_reward      BIT(1)                NOT NULL,
    CONSTRAINT pk_parent_notification PRIMARY KEY (id)
);