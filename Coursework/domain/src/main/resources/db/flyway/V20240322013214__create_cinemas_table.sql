CREATE TABLE cinemas
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city_id INT NOT NULL,
    street TEXT NOT NULL,
    FOREIGN KEY (city_id) REFERENCES cities(id) ON DELETE CASCADE
);

INSERT INTO cinemas (name, city_id, street)
VALUES ('La-ve', 1, 'Khreshchatyk 128'),
       ('Godel', 3, 'Heroiv Dnipra 91'),
       ('Blacarta', 1, 'Schevchenka 72');
