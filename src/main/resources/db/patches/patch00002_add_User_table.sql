CREATE TABLE IF NOT EXISTS user (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(256),
    password VARCHAR(256),
    privilege VARCHAR(256)
);
