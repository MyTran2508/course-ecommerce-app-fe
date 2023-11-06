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

INSERT IGNORE INTO `lectures`(id, created, creator, modifier, removed, updated, `name`, `url`, section_id)
       VALUES ('0', 1688521765499, 'admin', 'admin', 0, null, 'Course Structure and Project', 'https://', '0'),
        ('1', 1688521765499, 'admin', 'admin', 0, null, 'Watch Before You Start!', 'https://', '0'),
        ('2', 1688521765499, 'admin', 'admin', 0, null, 'A Brief Introduction to JavaScript', 'https://', '1'),
        ('3', 1688521765499, 'admin', 'admin', 0, null, 'Linking a JavaScript File', 'https://', '1'),

INSERT IGNORE INTO `sections`(id, created, creator, modifier, removed, updated, `name`, content_id)
       VALUES ('0', 1688521765499, 'admin', 'admin', 0, null, 'Welcome welcome', '0'),
        ('1', 1688521765499, 'admin', 'admin', 0, null, 'Javascript Foundamentals', '0'),

INSERT IGNORE INTO `content`(id, created, creator, modifier, removed, updated, description_id)
       VALUES ('0', 1688521765499, 'admin', 'admin', 0, null, '0'),
        ('1', 1688521765499, 'admin', 'admin', 0, null, '0'),

INSERT IGNORE INTO `description`(id, created, creator, modifier, removed, updated, details, requirements, target_consumers)
       VALUES ('0', 1688521765499, 'admin', 'admin', 0, null, 'No coding experience is necessary to take this course! I take you from beginner to expert!\\nAny computer and OS will work — Windows, macOS or Linux. We will set up your text editor the course.', 'JavaScript is the most popular programming language in the world. It powers the entire modern web. It provides millions of high-paying jobs all over the world.\\nThat''s why you want to learn JavaScript too. And you came to the right place!', 'Take this course if you want to gain a true and deep understanding of JavaScript\\nTake this course if you already know JavaScript and are looking for an advanced course. This course includes expert topics!'),

INSERT IGNORE INTO `images`(id, created, creator, modifier, removed, updated, is_default_image, url, course_id)
       VALUES ('0', 1688521765499, 'admin', 'admin', 0, null, 1, 'https://', 0),

INSERT IGNORE INTO `courses`(id, created, creator, modifier, removed, updated, author_name, `name`, price, sub_title, content_id, language_id, level_id, topic_id)
       VALUES ('0', 1688521765499, 'admin', 'admin', 0, null, 1, 'https://', 0),
