USE Webshop;

INSERT INTO T_Users(username, passwordHash)
VALUES ('customer1', 'test123');

INSERT INTO T_Items(name, description)
VALUES ('Apple', 'Round and red'),
       ('Pear', 'Oval and green');
