CREATE DATABASE IF NOT EXISTS Webshop;
USE Webshop;

CREATE TABLE T_Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    passwordHash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE T_Items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    category VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE T_Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,

    FOREIGN KEY (userId)
        REFERENCES T_Users(id)
);

CREATE TABLE T_OrderItems (
    orderId INT NOT NULL,
    itemId INT NOT NULL,
    nrOfItems INT NOT NULL,
    priceAtPurchase INT NOT NULL,

    PRIMARY KEY (orderId, itemId),

    FOREIGN KEY (orderId)
        REFERENCES T_Orders(id),

    FOREIGN KEY (itemId)
        REFERENCES T_Items(id)
);

INSERT INTO T_Items (name, description, category, price, stock)
VALUES  ('Apple', 'Round and red', 'Food', 6, 10),
        ('Pear', 'Oval and green', 'Food', 5, 25);

INSERT INTO T_Users (username, passwordHash, role)
VALUES  ('hans', '$2a$12$PEbbSceXMavk23T8Q6E1hOVMc9NeZj467KEACEpnFwo0o7RdRq6su', 'ADMIN'),
        ('helena', 'temporary', 'STAFF'),
        ('anna', 'temporary', 'CUSTOMER');
