CREATE TABLE sec_pass
(
    user_id       BIGINT NOT NULL,
    hash          VARCHAR(255) NOT NULL,
    salt          VARCHAR(255) NOT NULL
);

ALTER TABLE sec_pass
    ADD CONSTRAINT sec_pass_user_id_fk FOREIGN KEY (user_id) REFERENCES sec_user (id);