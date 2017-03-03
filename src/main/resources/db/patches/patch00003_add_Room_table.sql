CREATE TABLE IF NOT EXISTS room (
    id BIGINT NOT NULL PRIMARY KEY,
    user_id BIGINT REFERENCES user (id),
    number INT,
    title VARCHAR(256)
);
