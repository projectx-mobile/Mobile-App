create unique index family_requests_requests_id_uindex
    on family_requests (requests_id);

create unique index family_rewards_rewards_id_uindex
    on family_rewards (rewards_id);

alter table confirmation_token
    add constraint confirmation_token_sec_users_id_fk
        foreign key (user_id) references sec_users (id);

alter table family_requests
    add constraint family_requests_request_id_fk
        foreign key (requests_id) references request (id);

alter table family_requests
    add constraint family_requests_family_id_fk
        foreign key (family_id) references family (id);

alter table family_rewards
    add constraint family_rewards_reward_id_fk
        foreign key (rewards_id) references reward (id);

alter table family_rewards
    add constraint family_rewards_family_id_fk
        foreign key (family_id) references family (id);

alter table family_task_photo
    add constraint family_task_photo_family_tasks_id_fk
        foreign key (family_task_id) references family_tasks (id);

alter table family_tasks
    add constraint family_tasks_task_id_fk
        foreign key (task_id) references task (id);

alter table family_tasks
    add constraint family_tasks_family_id_fk
        foreign key (family_id) references family (id);

alter table request
    add constraint request_reward_id_fk
        foreign key (reward_id) references reward (id);


alter table reward_photo
    add constraint reward_photo_reward_id_fk
        foreign key (reward_id) references reward (id);


alter table sec_users
    add constraint sec_users_family_id_fk
        foreign key (family_id) references family (id);

alter table social_credentials
    add constraint social_credentials_sec_users_id_fk
        foreign key (user_id) references sec_users (id);

alter table task
    add constraint task_category_id_fk
        foreign key (category_id) references category (id);


alter table user_photo
    add constraint user_photo_sec_users_id_fk
        foreign key (user_id) references sec_users (id);
