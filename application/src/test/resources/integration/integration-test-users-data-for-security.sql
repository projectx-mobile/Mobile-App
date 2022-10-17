INSERT INTO sec_user(email, firebase_id, name, points, user_role, user_status)
values ('kidsapptestacc@gmail.com', 'UDlRPKRG8AaQfqXL3IL3mwXxtl32' ,'testUser', 12, 'PARENT', 'ACTIVE');

INSERT INTO client_app(app_id, updated)
VALUES ('eferwferc3627348', '2020-08-12 10:30:10'),
       ('fnhdjhdcdfe3fnjs', '2020-08-12 10:30:10');

INSERT INTO sec_user_client_apps(user_id, client_apps_id)
VALUES  (1, 1),
        (1, 2);
