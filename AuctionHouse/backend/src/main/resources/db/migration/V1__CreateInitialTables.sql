CREATE TABLE Users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE Items (
    id BIGINT NOT NULL AUTO_INCREMENT ,
    title VARCHAR(255),
    category VARCHAR(255),
    starting_price FLOAT,
    current_bid FLOAT,
    PRIMARY KEY (id)
);