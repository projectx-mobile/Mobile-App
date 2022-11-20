ALTER TABLE sec_user
    ADD CONSTRAINT fk_sec_user_on_child_notification FOREIGN KEY (child_notifications_id) REFERENCES child_notification (id);

ALTER TABLE sec_user
    ADD CONSTRAINT fk_sec_user_on_family FOREIGN KEY (family_id) REFERENCES family (id);

ALTER TABLE sec_user
    ADD CONSTRAINT fk_sec_user_on_parent_notification FOREIGN KEY (parent_notifications_id) REFERENCES parent_notification (id);

ALTER TABLE user_photo
    ADD CONSTRAINT fk_user_photo_on_user FOREIGN KEY (user_id) REFERENCES sec_user (id);

ALTER TABLE task
    ADD CONSTRAINT fk_task_on_category FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE reward_request
    ADD CONSTRAINT fk_reward_request_on_reward FOREIGN KEY (reward_id) REFERENCES reward (id);

ALTER TABLE reward_request
    ADD CONSTRAINT fk_reward_request_on_user FOREIGN KEY (user_id) REFERENCES sec_user (id);

ALTER TABLE reward_request_photo
    ADD CONSTRAINT fk_reward_request_photo_on_reward_request FOREIGN KEY (reward_request_id) REFERENCES reward_request (id);

ALTER TABLE reward_photo
    ADD CONSTRAINT fk_reward_photo_on_reward FOREIGN KEY (reward_id) REFERENCES reward (id);

ALTER TABLE family_task
    ADD CONSTRAINT fk_family_task_on_family FOREIGN KEY (family_id) REFERENCES family (id);

ALTER TABLE family_task
    ADD CONSTRAINT fk_family_task_on_task FOREIGN KEY (task_id) REFERENCES task (id);

ALTER TABLE family_task
    ADD CONSTRAINT fk_family_task_on_user FOREIGN KEY (user_id) REFERENCES sec_user (id);

ALTER TABLE family_task_photo
    ADD CONSTRAINT fk_family_task_photo_on_family_task FOREIGN KEY (family_task_id) REFERENCES family_task (id);

ALTER TABLE family_task_user
    ADD CONSTRAINT fk_family_task_user_on_family_task FOREIGN KEY (family_task_id) REFERENCES family_task (id);

ALTER TABLE family_task_user
    ADD CONSTRAINT fk_family_task_user_on_user FOREIGN KEY (user_id) REFERENCES sec_user (id);

ALTER TABLE family_rewards_requests
    ADD CONSTRAINT uc_family_rewards_requests_request UNIQUE (request_id);

ALTER TABLE family_rewards
    ADD CONSTRAINT uc_family_rewards_reward UNIQUE (reward_id);

ALTER TABLE family_rewards
    ADD CONSTRAINT fk_family_rewards_on_family FOREIGN KEY (family_id) REFERENCES family (id);

ALTER TABLE family_rewards
    ADD CONSTRAINT fk_family_rewards_on_reward FOREIGN KEY (reward_id) REFERENCES reward (id);

ALTER TABLE family_rewards_requests
    ADD CONSTRAINT fk_family_rewards_requests_on_family FOREIGN KEY (family_id) REFERENCES family (id);

ALTER TABLE family_rewards_requests
    ADD CONSTRAINT fk_family_rewards_requests_on_reward_request FOREIGN KEY (request_id) REFERENCES reward_request (id);

ALTER TABLE client_app
    ADD CONSTRAINT fk_client_app_on_user FOREIGN KEY (user_id) REFERENCES sec_user (id);

