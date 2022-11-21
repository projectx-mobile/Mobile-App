ALTER TABLE sec_user
    DROP FOREIGN KEY fk_sec_user_on_child_notification;

ALTER TABLE sec_user
    DROP FOREIGN KEY fk_sec_user_on_family;

ALTER TABLE sec_user
    DROP FOREIGN KEY fk_sec_user_on_parent_notification;

ALTER TABLE user_photo
    DROP FOREIGN KEY fk_user_photo_on_user;

ALTER TABLE task
    DROP FOREIGN KEY fk_task_on_category;

ALTER TABLE reward_request
    DROP FOREIGN KEY fk_reward_request_on_reward;

ALTER TABLE reward_request
    DROP FOREIGN KEY fk_reward_request_on_user;

ALTER TABLE reward_request_photo
    DROP FOREIGN KEY fk_reward_request_photo_on_reward_request;

ALTER TABLE reward_photo
    DROP FOREIGN KEY fk_reward_photo_on_reward;

ALTER TABLE family_task
    DROP FOREIGN KEY fk_family_task_on_family;

ALTER TABLE family_task
    DROP FOREIGN KEY fk_family_task_on_task;
ALTER TABLE family_task
   DROP FOREIGN KEY fk_family_task_on_user;

ALTER TABLE family_task_photo
    DROP FOREIGN KEY fk_family_task_photo_on_family_task;

ALTER TABLE family_task_user
    DROP FOREIGN KEY fk_family_task_user_on_family_task;

ALTER TABLE family_task_user
    DROP FOREIGN KEY fk_family_task_user_on_user;

ALTER TABLE family_rewards
    DROP FOREIGN KEY fk_family_rewards_on_family;

ALTER TABLE family_rewards
    DROP FOREIGN KEY fk_family_rewards_on_reward;

ALTER TABLE family_rewards_requests
    DROP FOREIGN KEY fk_family_rewards_requests_on_family;

ALTER TABLE family_rewards_requests
    DROP FOREIGN KEY fk_family_rewards_requests_on_reward_request;

ALTER TABLE client_app
    DROP FOREIGN KEY fk_client_app_on_user;