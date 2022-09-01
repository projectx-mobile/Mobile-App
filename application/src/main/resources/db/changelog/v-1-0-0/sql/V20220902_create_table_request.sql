CREATE TABLE request
(
    reward_id         BIGINT NOT NULL,
    user_id           BIGINT NOT NULL,
    request_status    VARCHAR(255) NOT NULL CHECK (request_status in ('APPROVED', 'REJECT'))
);

