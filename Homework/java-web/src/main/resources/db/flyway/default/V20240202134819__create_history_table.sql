CREATE TABLE IF NOT EXISTS public."history"
(
    id SERIAL PRIMARY KEY,
    original_message TEXT NOT NULL,
    processed_message TEXT NOT NULL,
    algorithm VARCHAR(20) NOT NULL,
    date TIMESTAMP NOT NULL,
    operation_type VARCHAR(20) NOT NULL,
    user_id INTEGER NOT NULL REFERENCES users
);
