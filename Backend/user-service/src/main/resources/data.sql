INSERT IGNORE INTO `role`(id, created, creator, modifier, removed, updated, description, `name`)
       VALUES ('ROLE_ADMIN', null, 'admin', null, 0, null, 'ALL PERMISSION', 'ROLE_ADMIN'),
       ('ROLE_MANAGER', null, 'admin', null, 0, null, 'Permission to manage users, courses, view company statistics, and support users', 'ROLE_MANAGER'),
       ('ROLE_USER', null, 'admin', null, 0, null, 'As a customer, users are the one who views, searches for courses, and performs transactions', 'ROLE_USER');

INSERT IGNORE INTO `user`(id, created, creator, modifier, removed, updated, email, first_name, last_name, password, photos, telephone, username)
       VALUES ('1', null, null, null, 0, null, 'pntnoah1@gmail.com', 'admin', 'admin', '$2a$10$IKPvCwZfK0sbqeC/W5N1vOlol8GwuJN8q1f5WgLqaYe7TgnBxIbU.', null, '0123456789', 'admin'),
       ('2', null, null, null, 0, null, 'pntnoah2@gmail.com', 'manager', 'manager', '$2a$10$IKPvCwZfK0sbqeC/W5N1vOlol8GwuJN8q1f5WgLqaYe7TgnBxIbU.', null, '0393023681', 'manager'),
       ('3', null, null, null, 0, null, 'keyhoangvu@gmail.com', 'user', 'user', '$2a$10$IKPvCwZfK0sbqeC/W5N1vOlol8GwuJN8q1f5WgLqaYe7TgnBxIbU.', null, '0937566870', 'user');

-- INSERT IGNORE INTO `users_roles`(user_id, role_id)
--        VALUES ('1', 'ROLE_ADMIN'),
--        ('2', 'ROLE_MANAGER'),
--        ('3', 'ROLE_USER');
