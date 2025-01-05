-- Drop foreign key dependent tables first

-- Drop the tables in reverse order of dependencies
DROP TABLE film_actors CASCADE CONSTRAINTS;
DROP TABLE seats CASCADE CONSTRAINTS;
DROP TABLE showing CASCADE CONSTRAINTS;
DROP TABLE screening_room CASCADE CONSTRAINTS;
DROP TABLE films CASCADE CONSTRAINTS;
DROP TABLE actors CASCADE CONSTRAINTS;

/*
-- Drop the existing sequences
DROP SEQUENCE seq_film_id;
DROP SEQUENCE seq_actor_id;
DROP SEQUENCE seq_showing_id;
DROP SEQUENCE seq_room_id
DROP SEQUENCE seq_seat_id;
*/

DROP TRIGGER trg_film_id;
DROP TRIGGER trg_actor_id;
DROP TRIGGER trg_room_id;
DROP TRIGGER trg_showing_id;
DROP TRIGGER trg_seat_id;




-- Drop the existing sequence if it exists
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seq_film_id';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN -- Ignore "sequence does not exist" error
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seq_actor_id';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN -- Ignore "sequence does not exist" error
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seq_showing_id';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN -- Ignore "sequence does not exist" error
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seq_room_id';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN -- Ignore "sequence does not exist" error
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seq_seat_id';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN -- Ignore "sequence does not exist" error
            RAISE;
        END IF;
END;
/
