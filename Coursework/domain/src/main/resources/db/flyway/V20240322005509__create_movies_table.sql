CREATE TABLE movies
(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    duration INT NOT NULL,
    releaseDate TIMESTAMP NOT NULL,
    country VARCHAR(255) NOT NULL,
    ageLimit INT NOT NULL,
    genres TEXT NOT NULL
);

INSERT INTO movies (title, description, duration, releaseDate, country, ageLimit, genres)
VALUES ('Avatar',
        'The films follow a U.S. Marine named Jake Sully (Sam Worthington) who becomes part of a program in which human colonizers explore and exploit an alien world called Pandora. ',
        134, TO_TIMESTAMP('2009-12-17 00:00:00.000000000', 'YY-MM-DD HH24:MI:SS.FF'),
        'United States', 13, 'ADVENTURE, FANTASY'),
       ('Titanic',
        'The movie is about the 1912 sinking of the RMS Titanic. It stars Kate Winslet and Leonardo DiCaprio. The two play characters who are of different social classes. They fall in love after meeting aboard the ship, but it was not good for a rich girl to fall in love with a poor boy in 1912.',
        190, TO_TIMESTAMP('1997-11-18 00:00:00.000000000', 'YY-MM-DD HH24:MI:SS.FF'),
        'United States', 13, 'ROMANCE, ADVENTURE'),
       ('Iron Man 3',
        'Marvel''s Iron Man 3 pits brash-but-brilliant industrialist Tony Stark/Iron Man against an enemy whose reach knows no bounds. When Stark finds his personal world destroyed at his enemy''s hands, he embarks on a harrowing quest to find those responsible. This journey, at every turn, will test his mettle.',
        130, TO_TIMESTAMP('2013-04-18 00:00:00.000000000', 'YY-MM-DD HH24:MI:SS.FF'),
        'United States', 13, 'ACTION, ADVENTURE');
