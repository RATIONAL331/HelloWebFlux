CREATE TABLE users
(
    id      BIGINT auto_increment,
    name    VARCHAR(50),
    balance INT,
    PRIMARY KEY (id)
);

CREATE TABLE user_transaction
(
    id               BIGINT auto_increment,
    user_id          BIGINT,
    amount           INT,
    transaction_date TIMESTAMP,
    PRIMARY KEY (id)
);
