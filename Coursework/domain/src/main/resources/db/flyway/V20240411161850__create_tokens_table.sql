CREATE TABLE tokens
(
    id        SERIAL PRIMARY KEY,
    token VARCHAR NOT NULL,
    user_id INT NOT NULL,
    expire_time TIMESTAMP NOT NULL,
    is_used BOOLEAN NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
