CREATE TABLE user_photo
(
    user_id     BIGINT NOT NULL,
    photo       VARCHAR(255)
);


ALTER TABLE user_photo
    ADD CONSTRAINT user_user_photo_fk FOREIGN KEY (user_id) REFERENCES sec_users (id);