CREATE TABLE movies
(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duration INT NOT NULL,
    releaseDate TIMESTAMP NOT NULL,
    country VARCHAR(255) NOT NULL,
    ageLimit INT NOT NULL,
    genre VARCHAR(100) NOT NULL,
    image bytea NOT NULL
);
