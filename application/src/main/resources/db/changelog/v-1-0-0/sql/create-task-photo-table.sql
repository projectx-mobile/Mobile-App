CREATE TABLE task_photo
(
    task_id     BIGINT NOT NULL,
    photo       VARCHAR(255)
);

ALTER TABLE task_photo
    ADD CONSTRAINT task_photo_task_id_fk FOREIGN KEY (task_id) REFERENCES task (id);