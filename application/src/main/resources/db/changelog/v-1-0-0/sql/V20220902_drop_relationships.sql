alter table kids_app.reward_photo
    drop foreign key reward_photo_reward_id_fk;

alter table kids_app.user_photo
    drop foreign key user_photo_sec_users_id_fk;

alter table kids_app.task_photo
    drop foreign key task_photo_task_id_fk;

alter table kids_app.reward
    drop foreign key reward_sec_users_family_id_fk;

alter table kids_app.request
    drop foreign key request_reward_id_fk;

alter table kids_app.request
    drop foreign key request_sec_users_id_fk;

alter table kids_app.family_task
    drop foreign key family_task_sec_users_family_id_fk;

alter table kids_app.family_task
    drop foreign key family_task_task_id_fk;




