alter table confirmation_token
    drop foreign key confirmation_token_sec_users_id_fk;


alter table family_requests
    drop foreign key family_requests_family_id_fk;


alter table family_requests
    drop foreign key family_requests_request_id_fk;

alter table family_rewards
    drop foreign key family_rewards_reward_id_fk;


alter table family_rewards
    drop foreign key family_rewards_family_id_fk;

alter table family_task_photo
    drop foreign key family_task_photo_family_tasks_id_fk;

alter table family_tasks
    drop foreign key family_tasks_family_id_fk;

alter table family_tasks
    drop foreign key family_tasks_task_id_fk;

alter table request
    drop foreign key request_reward_id_fk;

alter table reward_photo
    drop foreign key reward_photo_reward_id_fk;

alter table sec_users
    drop foreign key sec_users_family_id_fk;

alter table social_credentials
    drop foreign key social_credentials_sec_users_id_fk;