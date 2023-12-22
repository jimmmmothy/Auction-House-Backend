CREATE TABLE Bids (
    id BIGINT NOT NULL AUTO_INCREMENT,
    item_id BIGINT NOT NULL,
    bidder_id BIGINT NOT NULL,
    bid_amount FLOAT NOT NULL,
    bid_time TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (item_id) REFERENCES Items(id),
    FOREIGN KEY (bidder_id) REFERENCES Users(id)
);