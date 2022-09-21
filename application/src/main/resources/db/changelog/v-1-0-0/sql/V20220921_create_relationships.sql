create unique index family_rewards_requests_request_id_uindex
    on family_rewards_requests (request_id);

create unique index family_rewards_requests_family_id_uindex
    on family_rewards_requests (family_id);


create unique index family_task_user_family_task_id_uindex
    on family_task_user (family_task_id);


create unique index family_task_user_user_id_uindex
    on family_task_user (user_id);

create unique index family_rewards_reward_id_uindex
    on family_rewards (reward_id);

create unique index family_rewards_family_id_uindex
    on family_rewards (family_id);

alter table confirmation_token
    add constraint confirmation_token_sec_users_id_fk
        foreign key (user_id) references sec_user (id);

alter table family_rewards_requests
    add constraint family_rewards_requests_request_id_fk
        foreign key (request_id) references reward_request (id);

alter table family_rewards_requests
    add constraint family_rewards_requests_family_id_fk
        foreign key (family_id) references family (id);

alter table family_rewards
    add constraint family_rewards_reward_id_fk
        foreign key (reward_id) references reward (id);

alter table family_rewards
    add constraint family_rewards_family_id_fk
        foreign key (family_id) references family (id);

alter table family_task_photo
    add constraint family_task_photo_family_task_id_fk
        foreign key (family_task_id) references family_task (id);

alter table family_task
    add constraint family_task_task_id_fk
        foreign key (task_id) references task (id);

alter table family_task
    add constraint family_task_user_id_fk
        foreign key (user_id) references sec_user (id);

alter table family_task
    add constraint family_task_family_id_fk
        foreign key (family_id) references family (id);

alter table family_task_user
    add constraint family_task_user_family_task_id_fk
        foreign key (family_task_id) references family_task (id);

alter table family_task_user
    add constraint family_task_user_user_id_fk
        foreign key (user_id) references sec_user (id);


alter table reward_request
    add constraint reward_request_reward_id_fk
        foreign key (reward_id) references reward (id);

alter table reward_request
    add constraint reward_request_user_id_fk
        foreign key (user_id) references sec_user (id);


alter table reward_photo
    add constraint reward_photo_reward_id_fk
        foreign key (reward_id) references reward (id);


alter table sec_user
    add constraint sec_user_family_id_fk
        foreign key (family_id) references family (id);

alter table social_credentials
    add constraint social_credentials_sec_users_id_fk
        foreign key (user_id) references sec_user (id);

alter table task
    add constraint task_category_id_fk
        foreign key (category_id) references category (id);


alter table user_photo
    add constraint user_photo_sec_user_id_fk
        foreign key (user_id) references sec_user (id);
