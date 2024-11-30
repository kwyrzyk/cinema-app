-- Insert sample data into foods table (Cinema Concessions)
INSERT INTO foods (name) VALUES ('Popcorn');
INSERT INTO foods (name) VALUES ('Nachos');
INSERT INTO foods (name) VALUES ('Chips');
INSERT INTO foods (name) VALUES ('Candy Bar');  -- Added a candy bar item for variety

-- Insert sample data into food_prices table (with portion_size)
INSERT INTO food_prices (id_food, portion_size, price) VALUES (1, 'Small', 5.99);    -- Small Popcorn
INSERT INTO food_prices (id_food, portion_size, price) VALUES (1, 'Medium', 7.99);   -- Medium Popcorn
INSERT INTO food_prices (id_food, portion_size, price) VALUES (1, 'Large', 9.99);    -- Large Popcorn
INSERT INTO food_prices (id_food, portion_size, price) VALUES (2, 'Small', 4.49);    -- Small Nachos
INSERT INTO food_prices (id_food, portion_size, price) VALUES (2, 'Large', 6.99);    -- Large Nachos
INSERT INTO food_prices (id_food, portion_size, price) VALUES (3, 'Small', 3.49);    -- Small Chips
INSERT INTO food_prices (id_food, portion_size, price) VALUES (3, 'Large', 5.99);    -- Large Chips
INSERT INTO food_prices (id_food, portion_size, price) VALUES (4, 'Standard', 2.99); -- Standard Candy Bar (fixed size)

-- Insert sample data into drinks table (Cinema Drinks)
INSERT INTO drinks (name) VALUES ('Soda');
INSERT INTO drinks (name) VALUES ('Water');
INSERT INTO drinks (name) VALUES ('Juice');

-- Insert sample data into drinks_prices table (with portion_size)
INSERT INTO drinks_prices (id_drink, portion_size, price) VALUES (1, 'Small', 2.49);    -- Small Soda
INSERT INTO drinks_prices (id_drink, portion_size, price) VALUES (1, 'Medium', 3.49);   -- Medium Soda
INSERT INTO drinks_prices (id_drink, portion_size, price) VALUES (1, 'Large', 4.49);    -- Large Soda
INSERT INTO drinks_prices (id_drink, portion_size, price) VALUES (2, 'Small', 1.49);    -- Small Water
INSERT INTO drinks_prices (id_drink, portion_size, price) VALUES (2, 'Large', 2.49);    -- Large Water
INSERT INTO drinks_prices (id_drink, portion_size, price) VALUES (3, 'Small', 2.99);    -- Small Juice

-- Insert sample data into discounts table
INSERT INTO discounts (price) VALUES (1.00);    -- Discount 1: $1 off
INSERT INTO discounts (price) VALUES (2.50);    -- Discount 2: $2.50 off

-- Insert sample data into discounts_positions table
-- Discount 1 applies to 2 medium popcorns (ID 1 from food_prices) and 3 small sodas (ID 1 from drinks_prices)
-- Discount 2 applies to 1 large nachos (ID 2 from food_prices) and 2 small waters (ID 2 from drinks_prices)
INSERT INTO discounts_positions (id_discount, id_food_price, id_drink_price, food_count, drinks_count) VALUES (1, 2, 1, 2, 3);   -- Discount 1: 2 medium popcorn and 3 small sodas
INSERT INTO discounts_positions (id_discount, id_food_price, id_drink_price, food_count, drinks_count) VALUES (2, 3, 2, 1, 2);   -- Discount 2: 1 large nachos and 2 small waters
