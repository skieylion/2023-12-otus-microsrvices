CREATE TABLE USERS
(
    username  VARCHAR(64) PRIMARY KEY,
    password  varchar(64),
    full_name VARCHAR(512),
    email     VARCHAR(128),
    phone     VARCHAR(128),
    roles     VARCHAR(1024)
);
