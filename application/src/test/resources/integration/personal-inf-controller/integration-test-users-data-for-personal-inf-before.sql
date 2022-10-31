
INSERT INTO family(id) VALUES ('familyTest1');

INSERT INTO parent_notification(all_off, new_request, new_reward, new_task_status)
VALUES (1, 1, 1, 0);

INSERT INTO child_notification(all_off, confirm_reward, confirm_task, new_task, notification_period, penalty_and_bonus, task_reminder)
VALUES (1, 1, 1, 0, '10', 1, 0),
       (0, 0, 1, 0, '15', 1, 0);


INSERT INTO sec_user(email, firebase_id, name, points, user_role, user_status, family_id, parent_notifications_id)
VALUES ('parent.test1@gmail.com', 'zsWxQ4H18dRTccN8vUKjEHqh2tH3' ,'parentTest1', 0, 'PARENT', 'ACTIVE', 'familyTest1', 1);

INSERT INTO sec_user(email, firebase_id, name, points, user_role, user_status, family_id, child_notifications_id)
VALUES ('child.test1@gmail.com', 'N30bVnx3R0WICA9z3qJSusQZDNd2' ,'childTest1', 100, 'CHILD', 'ACTIVE', 'familyTest1', 1),
       ('child.test2@gmail.com', 'aukM0EixFkWnBRMIwytFYfaBNNv1' ,'childTest2', 15, 'CHILD', 'ACTIVE', 'familyTest1', 2);


INSERT INTO client_app(app_id, updated, user_id)
VALUES ('1:1234567890:ios:321abc456def7890', '2022-10-31 10:00:00', 1),
       ('1:1234567890:android:321abc456def7890', '2022-10-31 22:00:00', 2),
       ('1:1234432100:android:321cba456def7890', '2022-10-31 23:00:00', 3);

INSERT INTO user_photo(user_id, creation_date, path)
VALUES (1, '2022-10-31 10:00:00', 'test1.jpeg'),
       (2, '2022-10-31 10:00:00', 'test2.jpeg'),
       (3, '2022-10-31 10:00:00', 'test3.jpeg');