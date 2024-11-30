-- Drop foreign key dependent tables first
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE emp_film_actors CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE seats CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE showing CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE screening_room CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE films CASCADE CONSTRAINTS';
    EXECUTE IMMEDIATE 'DROP TABLE actors CASCADE CONSTRAINTS';
END;
/

-- You can also drop any sequences, if created previously
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seq_film_id';
END;
/


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
