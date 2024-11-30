-- Drop existing objects to avoid conflicts
DROP SEQUENCE seq_food_id;
DROP SEQUENCE seq_food_price_id;
DROP SEQUENCE seq_drink_id;
DROP SEQUENCE seq_drink_price_id;
DROP SEQUENCE seq_discount_id;
DROP SEQUENCE seq_position_id;

DROP TABLE discounts_positions CASCADE CONSTRAINTS;
DROP TABLE discounts CASCADE CONSTRAINTS;
DROP TABLE drinks_prices CASCADE CONSTRAINTS;
DROP TABLE drinks CASCADE CONSTRAINTS;
DROP TABLE food_prices CASCADE CONSTRAINTS;
DROP TABLE foods CASCADE CONSTRAINTS;
