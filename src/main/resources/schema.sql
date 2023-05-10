CREATE TABLE IF NOT EXISTS users
(
    id       INTEGER AUTO_INCREMENT PRIMARY KEY,
    email    CHARACTER VARYING(255) NOT NULL,
    login    CHARACTER VARYING(50)  NOT NULL,
    name     CHARACTER VARYING(50)  NOT NULL,
    birthday DATE                   NOT NULL
);

CREATE TABLE IF NOT EXISTS friendship
(
    user_id   INTEGER NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    friend_id INTEGER NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    status    BOOLEAN NOT NULL,
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS mpa
(
    mpa_id      INTEGER AUTO_INCREMENT PRIMARY KEY,
    name        CHARACTER VARYING(50) NOT NULL UNIQUE,
    description CHARACTER VARYING(100)
);

CREATE TABLE IF NOT EXISTS genre
(
    genre_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name     CHARACTER VARYING(50) UNIQUE
);

CREATE TABLE IF NOT EXISTS films
(
    id           INTEGER auto_increment primary key,
    name         CHARACTER VARYING(150) NOT NULL,
    description  CHARACTER VARYING(200) NOT NULL,
    release_date DATE                   NOT NULL,
    duration     INTEGER                NOT NULL,
    mpa_id       INTEGER                NOT NULL REFERENCES mpa (mpa_id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS film_genre
(
    film_id  INTEGER NOT NULL REFERENCES films ON DELETE CASCADE,
    genre_id INTEGER NOT NULL REFERENCES genre ON DELETE CASCADE,
    PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS likes
(
    film_id INTEGER NOT NULL REFERENCES films ON DELETE CASCADE,
    user_id INTEGER NOT NULL REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (film_id, user_id)
);