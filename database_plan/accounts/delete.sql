-- Drop the triggers (only if they exist)
BEGIN
    EXECUTE IMMEDIATE 'DROP TRIGGER orders_trigger';
EXCEPTION
    WHEN OTHERS THEN
        NULL;  -- Ignore error if the trigger doesn't exist
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TRIGGER accounts_trigger';
EXCEPTION
    WHEN OTHERS THEN
        NULL;  -- Ignore error if the trigger doesn't exist
END;
/

-- Drop the foreign key constraint (if it exists)
BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE orders DROP CONSTRAINT orders_id_account_fk';
EXCEPTION
    WHEN OTHERS THEN
        NULL;  -- Ignore error if the constraint doesn't exist
END;
/

-- Drop the tables
DROP TABLE orders CASCADE CONSTRAINTS;
DROP TABLE accounts CASCADE CONSTRAINTS;

-- Drop the sequences
DROP SEQUENCE orders_seq;
DROP SEQUENCE accounts_seq;
