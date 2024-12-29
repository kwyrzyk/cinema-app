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

BEGIN
    EXECUTE IMMEDIATE 'DROP TRIGGER order_item_trigger';
EXCEPTION
    WHEN OTHERS THEN
        NULL;  -- Ignore error if the trigger doesn't exist
END;
/

BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE order_item DROP CONSTRAINT fk_id_order';
EXCEPTION
    WHEN OTHERS THEN
        NULL;  -- Ignore error if the constraint doesn't exist
END;
/



-- Drop the tables
DROP TABLE orders CASCADE CONSTRAINTS;
DROP TABLE accounts CASCADE CONSTRAINTS;
DROP TABLE order_item CASCADE CONSTRAINT;
-- Drop the sequences
DROP SEQUENCE orders_seq;
DROP SEQUENCE accounts_seq;
DROP SEQUENCE seq_order_item_id;
