-- Create the 'films' table
CREATE TABLE films (
    id_film INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    short_description TEXT,
    long_description TEXT,
    rating DECIMAL(3, 1) -- Example: 8.5 for movie ratings
);

-- Create the 'actors' table
CREATE TABLE actors (
    id_actor INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    birth_date DATE
);

-- Create the junction table 'emp_film_actors'
CREATE TABLE emp_film_actors (
    id_film INT NOT NULL,
    id_actor INT NOT NULL,
    role VARCHAR(255),
    PRIMARY KEY (id_film, id_actor),
    FOREIGN KEY (id_film) REFERENCES films(id_film) ON DELETE CASCADE,
    FOREIGN KEY (id_actor) REFERENCES actors(id_actor) ON DELETE CASCADE
);
