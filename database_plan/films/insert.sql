-- Insert some films
INSERT INTO films (id_film, title, short_description, long_description, rating)
VALUES (seq_film_id.NEXTVAL, 'The Matrix', 'A computer hacker learns about the true nature of his reality.', 'Neo, a computer hacker, joins a group of rebels to learn about the true nature of his reality in a dystopian future where machines have enslaved humanity.', 8.7);

INSERT INTO films (id_film, title, short_description, long_description, rating)
VALUES (seq_film_id.NEXTVAL, 'Inception', 'A skilled thief is given a chance to have his criminal record erased if he can perform the ultimate heist.', 'A thief who enters the dreams of others to steal secrets from their subconscious is given the task of planting an idea in the mind of a CEO, but his latest mission becomes complicated.', 9.0);

INSERT INTO films (id_film, title, short_description, long_description, rating)
VALUES (seq_film_id.NEXTVAL, 'Interstellar', 'A team of explorers travel through a wormhole in space to ensure humanity’s survival.', 'In a dystopian future where Earth’s resources are dwindling, a team of astronauts travel through a wormhole in search of a new habitable planet for humans to colonize.', 8.6);

-- Insert some actors
INSERT INTO actors (id_actor, name, surname, birth_date)
VALUES (seq_actor_id.NEXTVAL, 'Keanu', 'Reeves', TO_DATE('1964-09-02', 'YYYY-MM-DD'));

INSERT INTO actors (id_actor, name, surname, birth_date)
VALUES (seq_actor_id.NEXTVAL, 'Leonardo', 'DiCaprio', TO_DATE('1974-11-11', 'YYYY-MM-DD'));

INSERT INTO actors (id_actor, name, surname, birth_date)
VALUES (seq_actor_id.NEXTVAL, 'Matthew', 'McConaughey', TO_DATE('1969-11-04', 'YYYY-MM-DD'));

-- Insert actor roles for films
INSERT INTO emp_film_actors (id_film, id_actor, role)
VALUES (1, 1, 'Neo');

INSERT INTO emp_film_actors (id_film, id_actor, role)
VALUES (2, 2, 'Cobb');

INSERT INTO emp_film_actors (id_film, id_actor, role)
VALUES (3, 3, 'Cooper');

-- Insert screening rooms
INSERT INTO screening_room (id_room, name, num_rows, seats_per_row)
VALUES (seq_room_id.NEXTVAL, 'Room A', 10, 20);

INSERT INTO screening_room (id_room, name, num_rows, seats_per_row)
VALUES (seq_room_id.NEXTVAL, 'Room B', 15, 25);

-- Insert showings for films
INSERT INTO showing (id_showing, id_film, id_room, show_time)
VALUES (seq_showing_id.NEXTVAL, 1, 1, TO_DATE('2024-12-01 18:30', 'YYYY-MM-DD HH24:MI'));

INSERT INTO showing (id_showing, id_film, id_room, show_time)
VALUES (seq_showing_id.NEXTVAL, 2, 2, TO_DATE('2024-12-01 20:30', 'YYYY-MM-DD HH24:MI'));

INSERT INTO showing (id_showing, id_film, id_room, show_time)
VALUES (seq_showing_id.NEXTVAL, 3, 1, TO_DATE('2024-12-02 17:00', 'YYYY-MM-DD HH24:MI'));

-- Insert some seats for showings
-- Room A seats (for 'The Matrix' showing)
INSERT INTO seats (id_seat, id_showing, row_number, seat_number, status)
VALUES (seq_seat_id.NEXTVAL, 1, 1, 1, 'available');

INSERT INTO seats (id_seat, id_showing, row_number, seat_number, status)
VALUES (seq_seat_id.NEXTVAL, 1, 1, 2, 'reserved');

INSERT INTO seats (id_seat, id_showing, row_number, seat_number, status)
VALUES (seq_seat_id.NEXTVAL, 1, 1, 3, 'booked');

-- Room B seats (for 'Inception' showing)
INSERT INTO seats (id_seat, id_showing, row_number, seat_number, status)
VALUES (seq_seat_id.NEXTVAL, 2, 1, 1, 'available');

INSERT INTO seats (id_seat, id_showing, row_number, seat_number, status)
VALUES (seq_seat_id.NEXTVAL, 2, 1, 2, 'reserved');

-- Room A seats (for 'Interstellar' showing)
INSERT INTO seats (id_seat, id_showing, row_number, seat_number, status)
VALUES (seq_seat_id.NEXTVAL, 3, 1, 1, 'available');

INSERT INTO seats (id_seat, id_showing, row_number, seat_number, status)
VALUES (seq_seat_id.NEXTVAL, 3, 1, 2, 'available');
