CREATE TABLE request
(
    reward_id         BIGINT NOT NULL,
    user_id           BIGINT NOT NULL,
    request_status    VARCHAR(255) NOT NULL CHECK (request_status in ('APPROVED', 'REJECT'))
);

ALTER TABLE request
    ADD CONSTRAINT request_reward_id_fk FOREIGN KEY (reward_id) REFERENCES reward (id);

ALTER TABLE request
    ADD CONSTRAINT request_user_id_fk FOREIGN KEY (user_id) REFERENCES sec_user (id);