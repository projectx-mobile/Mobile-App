CREATE TABLE family_task
(
    family_id    BIGINT NOT NULL,
    task_id      BIGINT NOT NULL
);


ALTER TABLE family_task
    ADD CONSTRAINT family_task_family_id_fk FOREIGN KEY (family_id) REFERENCES sec_users (family_id);
ALTER TABLE family_task
    ADD CONSTRAINT family_task_task_id_fk FOREIGN KEY (task_id) REFERENCES task (task_id);
