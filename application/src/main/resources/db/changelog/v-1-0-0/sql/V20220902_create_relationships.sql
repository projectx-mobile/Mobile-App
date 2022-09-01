create index family_id_index
    on kids_app.sec_users (family_id);

alter table kids_app.user_photo
    add constraint user_photo_sec_users_id_fk
        foreign key (user_id) references kids_app.sec_users (id);


alter table kids_app.task_photo
    add constraint task_photo_task_id_fk
        foreign key (task_id) references kids_app.task (id);

alter table kids_app.family_task
    add constraint family_task_sec_users_family_id_fk
        foreign key (family_id) references kids_app.sec_users (family_id);

alter table kids_app.family_task
    add constraint family_task_task_id_fk
        foreign key (task_id) references kids_app.task (id);

alter table kids_app.reward_photo
    add constraint reward_photo_reward_id_fk
        foreign key (reward_id) references kids_app.reward (id);

alter table kids_app.reward
    add constraint reward_sec_users_family_id_fk
        foreign key (family_id) references kids_app.sec_users (family_id);

alter table kids_app.request
    add constraint request_reward_id_fk
        foreign key (reward_id) references kids_app.reward (id);

alter table kids_app.request
    add constraint request_sec_users_id_fk
        foreign key (user_id) references kids_app.sec_users (id);


