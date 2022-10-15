INSERT INTO sec_user(email, firebase_id, name, points, user_role, user_status)
values ('kidsapptestacc@gmail.com', 'UDlRPKRG8AaQfqXL3IL3mwXxtl32' ,'dima', 12, 'PARENT', 'ACTIVE');

INSERT INTO client_app(app_id, updated)
VALUES ('app_id1', '2022-10-15 00:00:00'),
       ('app_id2', '2022-10-14 00:00:00'),
       ('app_id3', '2022-10-13 00:00:00'),
       ('app_id4', '2022-10-12 00:00:00');

INSERT INTO sec_user_client_apps(user_id, client_apps_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4);
