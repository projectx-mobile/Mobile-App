CREATE TABLE reward_photo
(
    reward_id     BIGINT NOT NULL,
    photo         VARCHAR(255)
);



ALTER TABLE reward_photo
    ADD CONSTRAINT reward_photo_raward_id_fk FOREIGN KEY (reward_id) REFERENCES reward (id);