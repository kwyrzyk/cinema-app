
CREATE TABLE showing (
    id_showing INT AUTO_INCREMENT PRIMARY KEY,
    id_film INT NOT NULL,              -- Foreign key to the film table
    id_room INT NOT NULL,              -- Foreign key to the screening_room table
    show_time DATETIME NOT NULL,       -- Time of the showing
    FOREIGN KEY (id_room) REFERENCES screening_room(id_room) ON DELETE CASCADE
);


CREATE TABLE screening_room (
    id_room INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,        -- Room name (e.g., "Room A")
    num_rows INT NOT NULL,             -- Number of rows in the room
    seats_per_row INT NOT NULL         -- Seats per row
);

CREATE TABLE seats (
    id_seat INT AUTO_INCREMENT PRIMARY KEY,
    id_showing INT NOT NULL,           -- Foreign key to the showing table
    row_number INT NOT NULL,           -- Row number
    seat_number INT NOT NULL,          -- Seat number
    status ENUM('available', 'reserved', 'booked') DEFAULT 'available', -- Seat status
    FOREIGN KEY (id_showing) REFERENCES showing(id_showing) ON DELETE CASCADE,
    UNIQUE (id_showing, row_number, seat_number) -- Ensure unique seats per showing
);

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
