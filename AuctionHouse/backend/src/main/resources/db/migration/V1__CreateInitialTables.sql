CREATE TABLE Users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    role VARCHAR(10) NOT NULL,
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
    title VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    starting_price FLOAT NOT NULL,
    current_bid FLOAT,
    description JSON,
    posted_by_user_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (posted_by_user_id) REFERENCES Users(id)
);