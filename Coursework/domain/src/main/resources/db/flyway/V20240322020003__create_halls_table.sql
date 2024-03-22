CREATE TABLE halls
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    rows INT NOT NULL,
    columns INT NOT NULL,
    cinema_id INT NOT NULL,
    FOREIGN KEY (cinema_id) REFERENCES cinemas(id) ON DELETE CASCADE
);

INSERT INTO halls (name, rows, columns, cinema_id)
VALUES ('Hall 1', 3, 5, 1),
       ('Hall 1', 2, 5, 2),
       ('Hall 2', 6, 2, 2),
       ('Hall 1', 4, 3, 3);
