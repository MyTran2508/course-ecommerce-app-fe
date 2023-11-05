INSERT IGNORE INTO `levels`(id, created, creator, modifier, removed, updated, description, `name`)
       VALUES ('0', 1688521765499, 'admin', 'admin', 0, null, 'Beginner', 0);
INSERT IGNORE INTO `levels`(id, created, creator, modifier, removed, updated, description, `name`)
       VALUES ('1', 1688521765499, 'admin', 'admin', 0, null, 'Intermediate', 1);
INSERT IGNORE INTO `levels`(id, created, creator, modifier, removed, updated, description, `name`)
       VALUES ('2', 1688521765499, 'admin', 'admin', 0, null, 'Expert', 2);
INSERT IGNORE INTO `levels`(id, created, creator, modifier, removed, updated, description, `name`)
       VALUES ('3', 1688521765499, 'admin', 'admin', 0, null, 'All Level', 3);

INSERT IGNORE INTO `language`(id, created, creator, modifier, removed, updated, `name`)
       VALUES ('1', 1688521765499, 'admin', 'admin', 0, null, 'Vietnamese'),
       ('2', 1688521765499, 'admin', 'admin', 0, null, 'English');

INSERT IGNORE INTO `categories`(id, created, creator, modifier, removed, updated, description, `name`)
       VALUES ('0', 1688521765499, 'admin', 'admin', 0, null, 'Progamming Languages', 'Progamming Languages'),
       ('1', 1688521765499, 'admin', 'admin', 0, null, 'Web Development', 'Web Development'),
       ('2', 1688521765499, 'admin', 'admin', 0, null, 'Mobile Development', 'Mobile Development'),
       ('3', 1688521765499, 'admin', 'admin', 0, null, 'Software Testing', 'Software Testing');

INSERT IGNORE INTO `topics`(id, created, creator, modifier, removed, updated, description, `name`, category_id)
       VALUES ('0', 1688521765499, 'admin', 'admin', 0, null, 'Python', 'Python', '0'),
       ('1', 1688521765499, 'admin', 'admin', 0, null, 'Java', 'Java', '0'),
       ('2', 1688521765499, 'admin', 'admin', 0, null, 'C#', 'C#', '0'),
       ('3', 1688521765499, 'admin', 'admin', 0, null, 'C++', 'C++', '0'),
       ('4', 1688521765499, 'admin', 'admin', 0, null, 'ReactJS', 'ReactJS', '1'),
       ('5', 1688521765499, 'admin', 'admin', 0, null, 'Angular', 'Angular', '1'),
       ('6', 1688521765499, 'admin', 'admin', 0, null, 'VueJS', 'VueJS', '1'),
       ('7', 1688521765499, 'admin', 'admin', 0, null, 'iSO Development', 'iSO Development', '2'),
       ('8', 1688521765499, 'admin', 'admin', 0, null, 'Android Development', 'Android Development', '2'),
       ('9', 1688521765499, 'admin', 'admin', 0, null, 'React Native', 'React Native', '2'),
       ('10', 1688521765499, 'admin', 'admin', 0, null, 'Selenium WebDriver', 'Selenium WebDriver', '3'),
       ('11', 1688521765499, 'admin', 'admin', 0, null, 'Postman', 'Postman', '3'),
       ('12', 1688521765499, 'admin', 'admin', 0, null, 'JUnit', 'JUnit', '3');