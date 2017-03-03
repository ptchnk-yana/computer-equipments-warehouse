CREATE TABLE IF NOT EXISTS request (
    id BIGINT NOT NULL PRIMARY KEY,
    user_id BIGINT REFERENCES user (id),
    title VARCHAR(256),
    description VARCHAR(1024),
    status VARCHAR(16)
);
