SET DATABASE SQL SYNTAX PGS TRUE;

INSERT INTO sec_user(email, name, points, user_role, user_status, family_id)
values ('kidsapptestacc@gmail.com', 'dima', 12, 'PARENT', 'ACTIVE', 'none');

INSERT INTO client_app(app_id, updated)
VALUES ('app_id1', '2022-10-15 00:00:00'),
       ('app_id2', '2022-10-14 00:00:00'),
       ('app_id3', '2022-10-13 00:00:00'),
       ('app_id4', '2022-10-12 00:00:00');

INSERT INTO sec_users_client_app(user_id, client_apps_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4);
