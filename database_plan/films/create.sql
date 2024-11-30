-- Create sequences again
CREATE SEQUENCE seq_film_id START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE seq_actor_id START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE seq_showing_id START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE seq_room_id START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE seq_seat_id START WITH 1 INCREMENT BY 1;


-- Create the 'films' table with smaller data types for descriptions
CREATE TABLE films (
    id_film INT PRIMARY KEY,
    title VARCHAR2(255) NOT NULL,
    short_description VARCHAR2(4000),  -- Replaced CLOB with VARCHAR2(4000)
    long_description VARCHAR2(4000),   -- Replaced CLOB with VARCHAR2(4000)
    rating NUMBER(3, 1)
);

-- Create the 'actors' table
CREATE TABLE actors (
    id_actor INT PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    surname VARCHAR2(100) NOT NULL,
    birth_date DATE
);

-- Create the junction table 'emp_film_actors'
CREATE TABLE emp_film_actors (
    id_film INT NOT NULL,
    id_actor INT NOT NULL,
    role VARCHAR2(255),
    PRIMARY KEY (id_film, id_actor),
    FOREIGN KEY (id_film) REFERENCES films(id_film) ON DELETE CASCADE,
    FOREIGN KEY (id_actor) REFERENCES actors(id_actor) ON DELETE CASCADE
);

-- Create the 'screening_room' table
CREATE TABLE screening_room (
    id_room INT PRIMARY KEY,
    name VARCHAR2(255) NOT NULL,
    num_rows INT NOT NULL,
    seats_per_row INT NOT NULL
);

-- Create the 'showing' table
CREATE TABLE showing (
    id_showing INT PRIMARY KEY,
    id_film INT NOT NULL,              -- Foreign key to the films table
    id_room INT NOT NULL,              -- Foreign key to the screening_room table
    show_time DATE NOT NULL,           -- Time of the showing
    FOREIGN KEY (id_room) REFERENCES screening_room(id_room) ON DELETE CASCADE,
    FOREIGN KEY (id_film) REFERENCES films(id_film) ON DELETE CASCADE
);

-- Create the 'seats' table
CREATE TABLE seats (
    id_seat INT PRIMARY KEY,
    id_showing INT NOT NULL,           -- Foreign key to the showing table
    row_number INT NOT NULL,           -- Row number
    seat_number INT NOT NULL,          -- Seat number
    status VARCHAR2(20) DEFAULT 'available',  -- Seat status (available, reserved, booked)
    FOREIGN KEY (id_showing) REFERENCES showing(id_showing) ON DELETE CASCADE,
    CONSTRAINT unique_seat UNIQUE (id_showing, row_number, seat_number) -- Ensure unique seats per showing
);

-- Create a sequence for generating IDs (Oracle does not support AUTO_INCREMENT)
CREATE SEQUENCE seq_film_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_actor_id START WITH 1 INCREMENT BY 1;
