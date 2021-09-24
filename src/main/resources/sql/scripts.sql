create database dbtest;

use dbtest;

CREATE TABLE users (
                       id INT NOT NULL AUTO_INCREMENT,
                       username VARCHAR(45) NOT NULL,
                       password VARCHAR(45) NOT NULL,
                       enabled INT NOT NULL,
                       PRIMARY KEY (id)
);

create table authorities (
                    `id` int not null AUTO_INCREMENT,
                    `customer_id` int not null,
                    `name` varchar(50) not null,
                    PRIMARY KEY (`id`),
                    KEY `customer_id` (`customer_id`),
                    CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`customer_id`)
                    REFERENCES `customer` (`id`)
);

INSERT INTO dbtest.users VALUES (NULL, 'happy', '12345', '1');
INSERT INTO dbtest.authorities VALUES (NULL, 2, 'WRITE');

CREATE TABLE customer (
                          id INT NOT NULL AUTO_INCREMENT,
                          email VARCHAR(45) NOT NULL,
                          pwd VARCHAR(200) NOT NULL,
                          role VARCHAR(45) NOT NULL,
                          PRIMARY KEY (id)
);

INSERT INTO dbtest.customer VALUES (NULL, 'elena@dazt.com', '12345', 'admin');

INSERT INTO dbtest.customer VALUES (NULL, 'diego@dazt.com', '$2a$12$4qAmqe8oDNrUIUnHQ6wHV.gYenIMg637QhpL6nlnN7FlHBc7swPkW', 'admin');