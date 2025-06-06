-- Insert data into accounts table
-- id_account will be auto-generated using the accounts_seq sequence
INSERT INTO accounts (login, password, email, phone_number) 
VALUES ('behq', 'behq', 'behq1@example.com', '123456789');

INSERT INTO accounts (login, password, email, phone_number) 
VALUES ('user2', 'pass456', 'user2@example.com', '234-567-8901');

INSERT INTO accounts (login, password, email, phone_number) 
VALUES ('user3', 'pass789', 'user3@example.com', '345-678-9012');

-- Insert data into orders table
-- id_order will be auto-generated using the orders_seq sequence
INSERT INTO orders (id_account, price, order_date) 
VALUES (1, 49.99, TO_DATE('2024-01-01', 'YYYY-MM-DD'));

INSERT INTO orders (id_account, price, order_date) 
VALUES (2, 19.99, TO_DATE('2024-01-02', 'YYYY-MM-DD'));

INSERT INTO orders (id_account, price, order_date) 
VALUES (1, 79.99, TO_DATE('2024-01-03', 'YYYY-MM-DD'));

INSERT INTO orders (id_account, price, order_date) 
VALUES (3, 29.99, TO_DATE('2024-01-04', 'YYYY-MM-DD'));


-- Insert data into rewards_trigger table
-- id_reward will be auto-generated using the orders_seq sequence
INSERT INTO point_rewards (name, points_price) 
VALUES ('Branded Mug', 45);

INSERT INTO point_rewards (name, points_price) 
VALUES ('Baseball Cap', 30);

INSERT INTO point_rewards (name, points_price) 
VALUES ('Key ring', 10);