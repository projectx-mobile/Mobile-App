alter table confirmation_token
    drop foreign key confirmation_token_sec_user_id_fk;

alter table family_rewards_requests
    drop foreign key family_rewards_requests_family_id_fk;

alter table family_rewards_requests
    drop foreign key family_rewards_requests_request_id_fk;

alter table family_rewards
    drop foreign key family_rewards_reward_id_fk;

alter table family_rewards
    drop foreign key family_rewards_family_id_fk;

alter table family_task_photo
    drop foreign key family_task_photo_family_task_id_fk;

alter table family_task
    drop foreign key family_task_family_id_fk;

alter table family_task
    drop foreign key family_task_task_id_fk;

alter table family_task
    drop foreign key family_task_user_id_fk;

alter table family_task_user
    drop foreign key family_task_user_family_task_id_fk;

alter table family_task_user
    drop foreign key family_task_user_user_id_fk;

alter table reward_request
    drop foreign key reward_request_reward_id_fk;

alter table reward_request
        drop foreign key reward_request_user_id_fk;

alter table reward_photo
    drop foreign key reward_photo_reward_id_fk;

alter table sec_user
    drop foreign key sec_users_family_id_fk;

alter table task
    drop foreign key task_category_id_fk;

alter table social_credentials
    drop foreign key social_credentials_sec_user_id_fk;