CREATE DATABASE IF NOT EXISTS Webshop;

CREATE USER IF NOT EXISTS "webshop_user" IDENTIFIED BY "<placeholder>";
GRANT INSERT, UPDATE, DELETE, SELECT ON Webshop.* TO webshop_user;
FLUSH PRIVILEGES;

USE Webshop;

CREATE TABLE IF NOT EXISTS T_Users (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    passwordHash VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS T_Items (
    itemId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255) NOT NULL
);

INSERT INTO T_Users(username, passwordHash)
VALUES ('customer1', 'test');

INSERT INTO T_Items(name, description)
VALUES ('Apple', 'Round and red'),
       ('Pear', 'Oval and green');
