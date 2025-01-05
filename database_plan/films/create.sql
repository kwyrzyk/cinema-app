-- Create sequences again
CREATE SEQUENCE seq_film_id START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE seq_actor_id START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE seq_showing_id START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE seq_room_id START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE seq_seat_id START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE seq_tag_id START WITH 1 INCREMENT BY 1;



-- Create the 'films' table with smaller data types for descriptions
CREATE TABLE films (
    id_film INT PRIMARY KEY,
    title VARCHAR2(255) NOT NULL,
    short_description VARCHAR2(4000),  
    long_description VARCHAR2(4000),   
    rating NUMBER(3, 1),
    pegi INT,
    CONSTRAINT chk_pegi CHECK (pegi IN (3, 7, 12, 16, 18)) -- Ensures only valid PEGI values
);


-- Create the 'actors' table
CREATE TABLE actors (
    id_actor INT PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    surname VARCHAR2(100) NOT NULL,
    birth_date DATE
);

-- Create the junction table 'film_actors'
CREATE TABLE film_actors (
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


CREATE TABLE tags (
    id_tag INT PRIMARY KEY,
    name VARCHAR2(255) NOT NULL UNIQUE -- Unique tag name
);


CREATE TABLE film_tags (
    id_film INT NOT NULL, -- Foreign key to films table
    id_tag INT NOT NULL,  -- Foreign key to tags table
    PRIMARY KEY (id_film, id_tag), -- Composite primary key to ensure no duplicate tag for a film
    FOREIGN KEY (id_film) REFERENCES films(id_film) ON DELETE CASCADE,
    FOREIGN KEY (id_tag) REFERENCES tags(id_tag) ON DELETE CASCADE
);



-- Create a trigger to automatically assign IDs for tags
CREATE OR REPLACE TRIGGER trg_tag_id
BEFORE INSERT ON tags
FOR EACH ROW
BEGIN
    :NEW.id_tag := seq_tag_id.NEXTVAL;
END;
/



CREATE OR REPLACE TRIGGER trg_film_id
BEFORE INSERT ON films
FOR EACH ROW
BEGIN
    :NEW.id_film := seq_film_id.NEXTVAL;
END;
/


CREATE OR REPLACE TRIGGER trg_actor_id
BEFORE INSERT ON actors
FOR EACH ROW
BEGIN
    :NEW.id_actor := seq_actor_id.NEXTVAL;
END;
/


CREATE OR REPLACE TRIGGER trg_room_id
BEFORE INSERT ON screening_room
FOR EACH ROW
BEGIN
    :NEW.id_room := seq_room_id.NEXTVAL;
END;
/


CREATE OR REPLACE TRIGGER trg_showing_id
BEFORE INSERT ON showing
FOR EACH ROW
BEGIN
    :NEW.id_showing := seq_showing_id.NEXTVAL;
END;
/



CREATE OR REPLACE TRIGGER trg_seat_id
BEFORE INSERT ON seats
FOR EACH ROW
BEGIN
    :NEW.id_seat := seq_seat_id.NEXTVAL;
END;
/



CREATE OR REPLACE TRIGGER insert_seats_for_showing
AFTER INSERT ON showing
FOR EACH ROW
DECLARE
    -- Use %TYPE to dynamically match the column types
    v_num_rows screening_room.num_rows%TYPE;
    v_seats_per_row screening_room.seats_per_row%TYPE;
    v_row_number screening_room.num_rows%TYPE;
    v_seat_number screening_room.seats_per_row%TYPE;
BEGIN
    -- Retrieve the number of rows and seats per row from the screening_room table
    SELECT num_rows, seats_per_row
    INTO v_num_rows, v_seats_per_row
    FROM screening_room
    WHERE id_room = :NEW.id_room;

    -- Loop through each row and seat
    FOR v_row_number IN 1..v_num_rows LOOP
        FOR v_seat_number IN 1..v_seats_per_row LOOP
            INSERT INTO seats (id_seat, id_showing, row_number, seat_number, status)
            VALUES (
                SEQ_SEAT_ID.NEXTVAL,  -- Assuming a sequence for unique seat IDs
                :NEW.id_showing,
                v_row_number,
                v_seat_number,
                'available'  -- Default status
            );
        END LOOP;
    END LOOP;
END;
/