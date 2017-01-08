CREATE TABLE IF NOT EXISTS device (
	id BIGINT NOT NULL PRIMARY KEY,
	room_id BIGINT REFERENCES room (id),
	device_type_id BIGINT REFERENCES device_type (id),
        hash VARCHAR(256),
        description VARCHAR(256)
);
