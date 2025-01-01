-- Create sequences for generating primary keys
CREATE SEQUENCE seq_food_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_food_price_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_drink_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_drink_price_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_discount_id START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_position_id START WITH 1 INCREMENT BY 1;

-- Create the foods table
CREATE TABLE foods (
    id_food INT PRIMARY KEY,
    name VARCHAR2(255) NOT NULL
);

-- Create the food_prices table with renamed column
CREATE TABLE food_prices (
    id_food_price INT PRIMARY KEY,
    id_food INT NOT NULL,
    portion_size VARCHAR2(50) NOT NULL,  -- Renamed from "size" to "portion_size"
    price NUMBER(10, 2) NOT NULL,
    FOREIGN KEY (id_food) REFERENCES foods(id_food) ON DELETE CASCADE
);

-- Create the drinks table
CREATE TABLE drinks (
    id_drink INT PRIMARY KEY,
    name VARCHAR2(255) NOT NULL
);

-- Create the drinks_prices table with renamed column
CREATE TABLE drinks_prices (
    id_drink_price INT PRIMARY KEY,
    id_drink INT NOT NULL,
    portion_size VARCHAR2(50) NOT NULL,  -- Renamed from "size" to "portion_size"
    price NUMBER(10, 2) NOT NULL,
    FOREIGN KEY (id_drink) REFERENCES drinks(id_drink) ON DELETE CASCADE
);

-- Create the discounts table
CREATE TABLE discounts (
    id_discount INT PRIMARY KEY,
    price NUMBER(10, 2) NOT NULL,
    start_time TIME,
    end_time TIME
);

-- Create the discounts_positions table
CREATE TABLE discounts_positions (
    id_position INT PRIMARY KEY,
    id_discount INT NOT NULL,
    id_food_price INT,
    id_drink_price INT,
    food_count INT DEFAULT 0,
    drinks_count INT DEFAULT 0,
    FOREIGN KEY (id_discount) REFERENCES discounts(id_discount) ON DELETE CASCADE,
    FOREIGN KEY (id_food_price) REFERENCES food_prices(id_food_price) ON DELETE CASCADE,
    FOREIGN KEY (id_drink_price) REFERENCES drinks_prices(id_drink_price) ON DELETE CASCADE
);

-- Trigger for foods
CREATE OR REPLACE TRIGGER trg_food_id
BEFORE INSERT ON foods
FOR EACH ROW
BEGIN
    :NEW.id_food := seq_food_id.NEXTVAL;
END;
/

-- Trigger for food_prices
CREATE OR REPLACE TRIGGER trg_food_price_id
BEFORE INSERT ON food_prices
FOR EACH ROW
BEGIN
    :NEW.id_food_price := seq_food_price_id.NEXTVAL;
END;
/

-- Trigger for drinks
CREATE OR REPLACE TRIGGER trg_drink_id
BEFORE INSERT ON drinks
FOR EACH ROW
BEGIN
    :NEW.id_drink := seq_drink_id.NEXTVAL;
END;
/

-- Trigger for drinks_prices
CREATE OR REPLACE TRIGGER trg_drink_price_id
BEFORE INSERT ON drinks_prices
FOR EACH ROW
BEGIN
    :NEW.id_drink_price := seq_drink_price_id.NEXTVAL;
END;
/

-- Trigger for discounts
CREATE OR REPLACE TRIGGER trg_discount_id
BEFORE INSERT ON discounts
FOR EACH ROW
BEGIN
    :NEW.id_discount := seq_discount_id.NEXTVAL;
END;
/

-- Trigger for discounts_positions
CREATE OR REPLACE TRIGGER trg_position_id
BEFORE INSERT ON discounts_positions
FOR EACH ROW
BEGIN
    :NEW.id_position := seq_position_id.NEXTVAL;
END;
/
