CREATE TABLE IF NOT EXISTS public."history"
(
    id SERIAL PRIMARY KEY,
    original_message TEXT,
    processed_message TEXT,
    algorithm VARCHAR(20) NOT NULL,
    date TIMESTAMP NOT NULL,
    operation_type VARCHAR(20),
    status VARCHAR(20) NOT NULL,
    user_id INTEGER NOT NULL REFERENCES users
);
