CREATE TABLE cities
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO cities (name)
VALUES ('Kyiv'),
       ('Lviv'),
       ('Cherkasy'),
       ('Dnipro');
